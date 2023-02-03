package de.ingef.deid.service.auth;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

/**
 * Placeholder for an API Authentication (e.g. Hashicorp Vault).
 *
 * This provider uses static API-tokens (like BasicAuth with {@code username:password}) which are preshared and are not secured in any way.
 *
 * You can configure such token a start-up. For a token like {@code test:test} add:
 * {@code --security.token.test=test}
 * to the command line.
 */
@RequiredArgsConstructor
public class ApiTokenAuthenticationProvider implements AuthenticationProvider {

	private final Map<String, String> apiToken;
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		String tokenId = auth.getName();
		String token = auth.getCredentials()
							  .toString();

		String savedSecret;
		if ((savedSecret = apiToken.get(tokenId)) != null && savedSecret.equals(token)) {
			return new UsernamePasswordAuthenticationToken(tokenId, token, List.of((GrantedAuthority) () -> "conquery"));
		} else {
			throw new
					BadCredentialsException("External system authentication failed");
		}
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}
}
