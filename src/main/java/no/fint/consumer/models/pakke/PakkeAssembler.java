package no.fint.consumer.models.pakke;

import no.fint.model.metamodell.Pakke;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class PakkeAssembler extends FintResourceAssembler<Pakke> {

    public PakkeAssembler() {
        super(PakkeController.class);
    }


    @Override
    public FintResourceSupport assemble(Pakke pakke , FintResource<Pakke> fintResource) {
        return createResourceWithId(pakke.getId().getIdentifikatorverdi(), fintResource, "id");
    }
    
    
    
}

