package no.fint.consumer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ConsumerProps {
    
    @Value("${fint.consumer.override-org-id:false}")
    private boolean overrideOrgId;

    @Value("${fint.consumer.default-client:FINT}")
    private String defaultClient;

    @Value("${fint.consumer.default-org-id:fint.no}")
    private String defaultOrgId;
    
    @Value("${fint.events.orgIds:fint.no}")
    private String[] orgs;

    
    public static final String CACHE_INITIALDELAY_KLASSE = "${fint.consumer.cache.initialDelay.klasse:60000}";
    public static final String CACHE_FIXEDRATE_KLASSE = "${fint.consumer.cache.fixedRate.klasse:900000}";
    
    public static final String CACHE_INITIALDELAY_PAKKE = "${fint.consumer.cache.initialDelay.pakke:70000}";
    public static final String CACHE_FIXEDRATE_PAKKE = "${fint.consumer.cache.fixedRate.pakke:900000}";
    
    public static final String CACHE_INITIALDELAY_RELASJON = "${fint.consumer.cache.initialDelay.relasjon:80000}";
    public static final String CACHE_FIXEDRATE_RELASJON = "${fint.consumer.cache.fixedRate.relasjon:900000}";
    

}
