package no.fint.consumer.models.klasse;

import no.fint.model.metamodell.Klasse;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class KlasseAssembler extends FintResourceAssembler<Klasse> {

    public KlasseAssembler() {
        super(KlasseController.class);
    }


    @Override
    public FintResourceSupport assemble(Klasse klasse , FintResource<Klasse> fintResource) {
        return createResourceWithId(klasse.getId().getIdentifikatorverdi(), fintResource, "id");
    }
    
    
    
}

