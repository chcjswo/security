package me.mocadev.movieservice.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import me.mocadev.movieservice.delegator.AuthenticationDelegator;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-11-05
 **/
@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

	private final AuthenticationDelegator delegator;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = authentication.getName();
		String password = String.valueOf(authentication.getCredentials());

		delegator.restAuth(userId, password);

		return new UsernamePasswordAuthenticationToken(userId, password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
	}
}
