
package no.fint.consumer.config;

public enum Constants {
;

    public static final String COMPONENT = "metamodell";
    public static final String COMPONENT_CONSUMER = COMPONENT + " consumer";
    public static final String CACHE_SERVICE = "CACHE_SERVICE";

    
    public static final String CACHE_INITIALDELAY_KLASSE = "${fint.consumer.cache.initialDelay.klasse:900000}";
    public static final String CACHE_FIXEDRATE_KLASSE = "${fint.consumer.cache.fixedRate.klasse:900000}";
    
    public static final String CACHE_INITIALDELAY_KONTEKST = "${fint.consumer.cache.initialDelay.kontekst:910000}";
    public static final String CACHE_FIXEDRATE_KONTEKST = "${fint.consumer.cache.fixedRate.kontekst:900000}";
    
    public static final String CACHE_INITIALDELAY_RELASJON = "${fint.consumer.cache.initialDelay.relasjon:920000}";
    public static final String CACHE_FIXEDRATE_RELASJON = "${fint.consumer.cache.fixedRate.relasjon:900000}";
    

}
