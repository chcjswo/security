package me.mocadev.authserver.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import me.mocadev.authserver.repository.auth.AuthRepository;
import me.mocadev.authserver.util.OtpCodeUtil;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-27
 **/
@RequiredArgsConstructor
@Service
public class OtpService {

	private final AuthRepository authRepository;

	public boolean checkOtp(String userId, String sourceOtp) {
		String targetOtp = authRepository.getOtp(userId);
		return targetOtp.equals(sourceOtp);
	}

	public String renewOtp(String userId) {
		String newOtp = OtpCodeUtil.generateOtpCode();
		authRepository.upsertOtp(userId, newOtp);
		return newOtp;
	}
}
