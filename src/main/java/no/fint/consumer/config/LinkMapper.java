package no.fint.consumer.config;

import no.fint.consumer.utils.RestEndpoints;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import no.fint.model.metamodell.Klasse;
import no.fint.model.metamodell.Pakke;
import no.fint.model.metamodell.Relasjon;

public class LinkMapper {

	public static Map<String, String> linkMapper(String contextPath) {
		return ImmutableMap.<String,String>builder()
			.put(Klasse.class.getName(), contextPath + RestEndpoints.KLASSE)
			.put(Pakke.class.getName(), contextPath + RestEndpoints.PAKKE)
			.put(Relasjon.class.getName(), contextPath + RestEndpoints.RELASJON)
			.build();
	}

}
