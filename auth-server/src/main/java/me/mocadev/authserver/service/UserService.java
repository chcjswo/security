package me.mocadev.authserver.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import me.mocadev.authserver.domain.user.User;
import me.mocadev.authserver.repository.auth.AuthRepository;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-27
 **/
@RequiredArgsConstructor
@Service
public class UserService {

	private final OtpService otpService;
	private final AuthRepository authRepository;

	public User createNewUser(String userId, String password) {
		return authRepository.createNewUser(new User(userId, password));
	}
}