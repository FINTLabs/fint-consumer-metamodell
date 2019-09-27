package no.fint.consumer.models.klasse;

import no.fint.model.resource.Link;
import no.fint.model.resource.metamodell.KlasseResource;
import no.fint.model.resource.metamodell.KlasseResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class KlasseLinker extends FintLinker<KlasseResource> {

    public KlasseLinker() {
        super(KlasseResource.class);
    }

    public void mapLinks(KlasseResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KlasseResources toResources(Collection<KlasseResource> collection) {
        KlasseResources resources = new KlasseResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(KlasseResource klasse) {
        if (!isNull(klasse.getId()) && !isEmpty(klasse.getId().getIdentifikatorverdi())) {
            return createHrefWithId(klasse.getId().getIdentifikatorverdi(), "id");
        }
        
        return null;
    }
    
}

