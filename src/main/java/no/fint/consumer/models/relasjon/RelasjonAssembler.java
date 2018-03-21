package no.fint.consumer.models.relasjon;

import no.fint.model.metamodell.Relasjon;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class RelasjonAssembler extends FintResourceAssembler<Relasjon> {

    public RelasjonAssembler() {
        super(RelasjonController.class);
    }


    @Override
    public FintResourceSupport assemble(Relasjon relasjon , FintResource<Relasjon> fintResource) {
        return createResourceWithId(relasjon.getId().getIdentifikatorverdi(), fintResource, "id");
    }
    
    
    
}

