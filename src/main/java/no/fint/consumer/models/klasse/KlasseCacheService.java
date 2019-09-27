package no.fint.consumer.models.klasse;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

import no.fint.cache.CacheService;
import no.fint.cache.model.CacheObject;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.relations.FintResourceCompatibility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import no.fint.model.metamodell.Klasse;
import no.fint.model.resource.metamodell.KlasseResource;
import no.fint.model.metamodell.MetamodellActions;
import no.fint.model.metamodell.kompleksedatatyper.Identifikator;

@Slf4j
@Service
@ConditionalOnProperty(name = "fint.consumer.cache.disabled.klasse", havingValue = "false", matchIfMissing = true)
public class KlasseCacheService extends CacheService<KlasseResource> {

    public static final String MODEL = Klasse.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private KlasseLinker linker;

    private JavaType javaType;

    private ObjectMapper objectMapper;

    public KlasseCacheService() {
        super(MODEL, MetamodellActions.GET_ALL_KLASSE, MetamodellActions.UPDATE_KLASSE);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, KlasseResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        props.getAssets().forEach(this::createCache);
    }

    @Scheduled(initialDelayString = Constants.CACHE_INITIALDELAY_KLASSE, fixedRateString = Constants.CACHE_FIXEDRATE_KLASSE)
    public void populateCacheAll() {
        props.getAssets().forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    @Override
    public void populateCache(String orgId) {
		log.info("Populating Klasse cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, MetamodellActions.GET_ALL_KLASSE, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<KlasseResource> getKlasseById(String orgId, String id) {
        return getOne(orgId, id.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(KlasseResource::getId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(id::equals)
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<KlasseResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<KlasseResource> to KlasseResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), KlasseResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(linker::mapLinks);
        if (MetamodellActions.valueOf(event.getAction()) == MetamodellActions.UPDATE_KLASSE) {
            if (event.getResponseStatus() == ResponseStatus.ACCEPTED || event.getResponseStatus() == ResponseStatus.CONFLICT) {
                List<CacheObject<KlasseResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
                addCache(event.getOrgId(), cacheObjects);
                log.info("Added {} cache objects to cache for {}", cacheObjects.size(), event.getOrgId());
            } else {
                log.debug("Ignoring payload for {} with response status {}", event.getOrgId(), event.getResponseStatus());
            }
        } else {
            List<CacheObject<KlasseResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
            updateCache(event.getOrgId(), cacheObjects);
            log.info("Updated cache for {} with {} cache objects", event.getOrgId(), cacheObjects.size());
        }
    }
}
