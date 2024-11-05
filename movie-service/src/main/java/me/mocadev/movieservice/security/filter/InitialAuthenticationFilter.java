package me.mocadev.movieservice.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import me.mocadev.movieservice.security.OtpAuthentication;
import me.mocadev.movieservice.security.OtpAuthenticationProvider;
import me.mocadev.movieservice.security.UsernamePasswordAuthentication;
import me.mocadev.movieservice.security.UsernamePasswordAuthenticationProvider;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-11-05
 **/
@Component
@RequiredArgsConstructor
public class InitialAuthenticationFilter extends OncePerRequestFilter {

	private final OtpAuthenticationProvider otpAuthenticationProvider;
	private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

	@Value("${jwt.signing-key}")
	private String jwtKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
		ServletException, IOException {
		String username = request.getHeader("username");
		String password = request.getHeader("password");
		String otp = request.getHeader("otp");

		if (StringUtils.isBlank(otp)) {
			UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(username, password);
			usernamePasswordAuthenticationProvider.authenticate(authentication);
		} else {
			Authentication authentication = new OtpAuthentication(username, otp);
			otpAuthenticationProvider.authenticate(authentication);

			SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

			String jwt = Jwts.builder()
				.claim("username", username)
				.signWith(secretKey)
				.compact();

			response.setHeader("Authorization", jwt);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/login");
	}
}
