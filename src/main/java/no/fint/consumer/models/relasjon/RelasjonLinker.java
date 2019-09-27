package no.fint.consumer.models.relasjon;

import no.fint.model.resource.Link;
import no.fint.model.resource.metamodell.RelasjonResource;
import no.fint.model.resource.metamodell.RelasjonResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
        RelasjonResources resources = new RelasjonResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(RelasjonResource relasjon) {
        if (!isNull(relasjon.getId()) && !isEmpty(relasjon.getId().getIdentifikatorverdi())) {
            return createHrefWithId(relasjon.getId().getIdentifikatorverdi(), "id");
        }
        
        return null;
    }
    
}

