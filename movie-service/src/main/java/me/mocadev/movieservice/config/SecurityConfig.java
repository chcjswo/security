package me.mocadev.movieservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-11-05
 **/
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final InitialAuthenticationFilter initialAuthenticationFilter;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(AbstractHttpConfigurer::disable);

		httpSecurity.addFilterBefore(initialAuthenticationFilter, BasicAuthenticationFilter.class);
		httpSecurity.addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

		httpSecurity.authorizeHttpRequests(c -> c.anyRequest().authenticated());

		return httpSecurity.build();
	}
}
