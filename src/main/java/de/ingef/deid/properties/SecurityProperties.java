package de.ingef.deid.properties;

import java.net.URI;
import java.util.Map;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {

	private String clientId;
	private String clientSecret;
	private URI wellKnownEndpoint;
	private Map<String, String> token;

}
