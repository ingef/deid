package de.ingef.deid.config;

import java.util.Map;
import java.util.Objects;

import com.google.common.collect.ImmutableList;
import de.ingef.deid.properties.SecurityProperties;
import de.ingef.deid.service.auth.ApiTokenAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private SecurityProperties securityProperties;



	@Bean
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		// bearer-only
		return new NullAuthenticatedSessionStrategy();
	}

	@Bean
	public AuthenticationProvider customAuthenticationProvider() {
		return new ApiTokenAuthenticationProvider(Objects.requireNonNull(securityProperties.getToken()));
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(ImmutableList.of("*")); // TODO configure
		configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean("tokenIntrospectionUrl")
	public String tokenIntrospectionUrl() {
		// TODO clean this up
		RestTemplate restTemplate = new RestTemplate();
		final ResponseEntity<Map<String, ?>>
				forObject = restTemplate.exchange(securityProperties.getWellKnownEndpoint(), HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Map<String,?>>() {});

		if(!forObject.getStatusCode().is2xxSuccessful()) {
			throw new IllegalStateException("Cannot retrieve wellKnown information");
		}
		final String introspectionEndpoint = (String) Objects.requireNonNull(forObject.getBody()).get("introspection_endpoint");
		log.info("Got introspection URI: '{}'", introspectionEndpoint);
		return introspectionEndpoint;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors().
				and()
				.authenticationProvider(customAuthenticationProvider())
				.authorizeHttpRequests()
					.requestMatchers("/swagger-ui", "/swagger-ui/*", "/v3/api-docs", "/v3/api-docs/*").permitAll()
					.anyRequest()
					.fullyAuthenticated()
				.and()
				.httpBasic()
				.and()
				// Never create or use a session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.oauth2ResourceServer(s -> {
					s.opaqueToken(t -> //TODO configure
										  t.introspectionUri(tokenIntrospectionUrl())//"http://auth.lyo-peva02/realms/Ingef/protocol/openid-connect/token/introspect")
										   .introspectionClientCredentials(securityProperties.getClientId(), securityProperties.getClientSecret()));
				} );
		return http.build();
	}
}
