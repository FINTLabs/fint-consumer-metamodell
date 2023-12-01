package no.fint.consumer.models.relasjon;

import no.fint.model.resource.AbstractCollectionResources;
import no.fint.model.resource.Link;
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
public class RelasjonLinker extends FintLinker<RelasjonResource> {

    public RelasjonLinker() {
        super(RelasjonResource.class);
    }

    public void mapLinks(RelasjonResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public RelasjonResources toResources(Collection<RelasjonResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public RelasjonResources toResources(Stream<RelasjonResource> stream, int offset, int size, int totalItems) {
        RelasjonResources resources = new RelasjonResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(RelasjonResource relasjon) {
        if (!isNull(relasjon.getId()) && !isEmpty(relasjon.getId().getIdentifikatorverdi())) {
            return createHrefWithId(relasjon.getId().getIdentifikatorverdi(), "id");
        }
        
        return null;
    }

    int[] hashCodes(RelasjonResource relasjon) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(relasjon.getId()) && !isEmpty(relasjon.getId().getIdentifikatorverdi())) {
            builder.add(relasjon.getId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

