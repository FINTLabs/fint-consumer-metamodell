package no.fint.consumer.config;

import no.fint.consumer.utils.RestEndpoints;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

import no.fint.model.metamodell.*;

public class LinkMapper {

	public static Map<String, String> linkMapper(String contextPath) {
		return ImmutableMap.<String,String>builder()
			.put(Klasse.class.getName(), contextPath + RestEndpoints.KLASSE)
			.put(Kontekst.class.getName(), contextPath + RestEndpoints.KONTEKST)
			.put(Relasjon.class.getName(), contextPath + RestEndpoints.RELASJON)
			/* .put(TODO,TODO) */
			.build();
	}

}
