package no.fint.consumer.models.klasse;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import no.fint.audit.FintAuditService;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.utils.RestEndpoints;
import no.fint.event.model.Event;
import no.fint.event.model.HeaderConstants;
import no.fint.event.model.Status;

import no.fint.model.relation.FintResource;
import no.fint.relations.FintRelationsMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import no.fint.model.metamodell.Klasse;
import no.fint.model.metamodell.MetamodellActions;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = RestEndpoints.KLASSE, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class KlasseController {

    @Autowired
    private KlasseCacheService cacheService;

    @Autowired
    private FintAuditService fintAuditService;

    @Autowired
    private KlasseAssembler assembler;

    @Autowired
    private ConsumerProps props;

    @GetMapping("/last-updated")
    public Map<String, String> getLastUpdated(@RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId) {
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        String lastUpdated = Long.toString(cacheService.getLastUpdated(orgId));
        return ImmutableMap.of("lastUpdated", lastUpdated);
    }

    @GetMapping("/cache/size")
     public ImmutableMap<String, Integer> getCacheSize(@RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId) {
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        return ImmutableMap.of("size", cacheService.getAll(orgId).size());
    }

    @PostMapping("/cache/rebuild")
    public void rebuildCache(@RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId) {
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        cacheService.rebuildCache(orgId);
    }

    @GetMapping
    public ResponseEntity getKlasse(
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client,
            @RequestParam(required = false) Long sinceTimeStamp) {
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        if (client == null) {
            client = props.getDefaultClient();
        }
        log.info("OrgId: {}, Client: {}", orgId, client);

        Event event = new Event(orgId, Constants.COMPONENT, MetamodellActions.GET_ALL_KLASSE, client);
        fintAuditService.audit(event);

        fintAuditService.audit(event, Status.CACHE);

        List<FintResource<Klasse>> klasse;
        if (sinceTimeStamp == null) {
            klasse = cacheService.getAll(orgId);
        } else {
            klasse = cacheService.getAll(orgId, sinceTimeStamp);
        }

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        return assembler.resources(klasse);
    }


    @GetMapping("/id/{id:.+}")
    public ResponseEntity getKlasseById(@PathVariable String id,
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client) {
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        if (client == null) {
            client = props.getDefaultClient();
        }
        log.info("Id: {}, OrgId: {}, Client: {}", id, orgId, client);

        Event event = new Event(orgId, Constants.COMPONENT, MetamodellActions.GET_KLASSE, client);
        fintAuditService.audit(event);

        fintAuditService.audit(event, Status.CACHE);

        Optional<FintResource<Klasse>> klasse = cacheService.getKlasseById(orgId, id);

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        if (klasse.isPresent()) {
            return assembler.resource(klasse.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}

