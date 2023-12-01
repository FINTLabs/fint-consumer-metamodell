package no.fint.consumer.models.kontekst;

import no.fint.model.resource.AbstractCollectionResources;
import no.fint.model.resource.Link;
import no.fint.model.resource.metamodell.KontekstResource;
import no.fint.model.resource.metamodell.KontekstResources;
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
public class KontekstLinker extends FintLinker<KontekstResource> {

    public KontekstLinker() {
        super(KontekstResource.class);
    }

    public void mapLinks(KontekstResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KontekstResources toResources(Collection<KontekstResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public KontekstResources toResources(Stream<KontekstResource> stream, int offset, int size, int totalItems) {
        KontekstResources resources = new KontekstResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(KontekstResource kontekst) {
        if (!isNull(kontekst.getId()) && !isEmpty(kontekst.getId().getIdentifikatorverdi())) {
            return createHrefWithId(kontekst.getId().getIdentifikatorverdi(), "id");
        }
        
        return null;
    }

    int[] hashCodes(KontekstResource kontekst) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(kontekst.getId()) && !isEmpty(kontekst.getId().getIdentifikatorverdi())) {
            builder.add(kontekst.getId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

