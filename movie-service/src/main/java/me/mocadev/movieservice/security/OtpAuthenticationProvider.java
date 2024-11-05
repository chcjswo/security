package me.mocadev.movieservice.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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
public class OtpAuthenticationProvider implements AuthenticationProvider {

	private final AuthenticationDelegator delegator;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = authentication.getName();
		String otp = String.valueOf(authentication.getCredentials());

		boolean result = delegator.restOtp(userId, otp);

		if (result) {
			return new OtpAuthentication(userId, otp);
		}

		throw new BadCredentialsException("Invalid OTP");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OtpAuthentication.class.isAssignableFrom(authentication);
	}
}
