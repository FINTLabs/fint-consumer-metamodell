package no.fint.consumer.models.klasse;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import no.fint.cache.CacheService;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.model.metamodell.kompleksedatatyper.Identifikator;
import no.fint.model.relation.FintResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import no.fint.model.metamodell.Klasse;
import no.fint.model.metamodell.MetamodellActions;

@Slf4j
@Service
public class KlasseCacheService extends CacheService<FintResource<Klasse>> {

    public static final String MODEL = Klasse.class.getSimpleName().toLowerCase();

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    public KlasseCacheService() {
        super(MODEL, MetamodellActions.GET_ALL_KLASSE);
    }

    @PostConstruct
    public void init() {
        Arrays.stream(props.getOrgs()).forEach(this::createCache);
    }

    @Scheduled(initialDelayString = ConsumerProps.CACHE_INITIALDELAY_KLASSE, fixedRateString = ConsumerProps.CACHE_FIXEDRATE_KLASSE)
    public void populateCacheAll() {
        Arrays.stream(props.getOrgs()).forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    private void populateCache(String orgId) {
		log.info("Populating Klasse cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, MetamodellActions.GET_ALL_KLASSE, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<FintResource<Klasse>> getKlasseById(String orgId, String id) {
        return getOne(orgId, (fintResource) -> Optional
                .ofNullable(fintResource)
                .map(FintResource::getResource)
                .map(Klasse::getId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(_id -> id.equals(_id))
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        update(event, new TypeReference<List<FintResource<Klasse>>>() {
        });
    }
}
