package me.mocadev.movieservice.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.mocadev.movieservice.security.UsernamePasswordAuthentication;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-11-05
 **/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Value("${jwt.signing-key}")
	private String signingKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
		ServletException, IOException {
		String jwt = request.getHeader("Authorization");

		SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

		Claims payload = Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(jwt)
			.getPayload();

		String username = String.valueOf(payload.get("username"));

		GrantedAuthority authority = new SimpleGrantedAuthority("user");

		UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(username, null, List.of(authority));

		SecurityContextHolder.getContext().setAuthentication(authentication);;

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getServletPath().equals("/login");
	}
}