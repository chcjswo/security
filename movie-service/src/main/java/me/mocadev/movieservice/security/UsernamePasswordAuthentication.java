package me.mocadev.movieservice.security;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-11-05
 **/
public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {
	public UsernamePasswordAuthentication(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public UsernamePasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}
}
