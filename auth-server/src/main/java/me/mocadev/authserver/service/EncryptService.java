package me.mocadev.authserver.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-28
 **/
@Service
@RequiredArgsConstructor
public class EncryptService {

	private final PasswordEncoder passwordEncoder;

	public String encrypt(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	public boolean matches(String source, String target) {
		return passwordEncoder.matches(source, target);
	}
}
