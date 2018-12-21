package no.fint.consumer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Component
public class ConsumerProps {

    @Value("${fint.consumer.override-org-id:false}")
    private boolean overrideOrgId;

    @Value("${fint.consumer.default-client:FINT}")
    private String defaultClient;

    @Value("${fint.consumer.default-org-id:fint.no}")
    private String defaultOrgId;

    public static final String CACHE_INITIALDELAY_KLASSE = "${fint.consumer.cache.initialDelay.klasse:60000}";
    public static final String CACHE_FIXEDRATE_KLASSE = "${fint.consumer.cache.fixedRate.klasse:1000000}";
    
    public static final String CACHE_INITIALDELAY_PAKKE = "${fint.consumer.cache.initialDelay.pakke:70000}";
    public static final String CACHE_FIXEDRATE_PAKKE = "${fint.consumer.cache.fixedRate.pakke:1000000}";
    
    public static final String CACHE_INITIALDELAY_RELASJON = "${fint.consumer.cache.initialDelay.relasjon:80000}";
    public static final String CACHE_FIXEDRATE_RELASJON = "${fint.consumer.cache.fixedRate.relasjon:1000000}";

    private Set<String> assets;

    @Autowired
    private void setupOrgs(@Value("${fint.events.orgIds:}") String[] orgs) {
        assets = new HashSet<>(Arrays.asList(orgs));
    }

    public String[] getOrgs() {
        return assets.toArray(new String[0]);
    }

}

