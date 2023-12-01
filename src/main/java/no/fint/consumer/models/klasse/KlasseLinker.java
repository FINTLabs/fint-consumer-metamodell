package no.fint.consumer.models.klasse;

import no.fint.model.resource.AbstractCollectionResources;
import no.fint.model.resource.Link;
import no.fint.model.resource.metamodell.KlasseResource;
import no.fint.model.resource.metamodell.KlasseResources;
import no.fint.model.resource.metamodell.RelasjonResource;
import no.fint.model.resource.metamodell.RelasjonResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
       return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public KlasseResources toResources(Stream<KlasseResource> stream, int offset, int size, int totalItems) {
        KlasseResources resources = new KlasseResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(KlasseResource klasse) {
        if (!isNull(klasse.getId()) && !isEmpty(klasse.getId().getIdentifikatorverdi())) {
            return createHrefWithId(klasse.getId().getIdentifikatorverdi(), "id");
        }
        
        return null;
    }

    int[] hashCodes(KlasseResource klasse) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(klasse.getId()) && !isEmpty(klasse.getId().getIdentifikatorverdi())) {
            builder.add(klasse.getId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

