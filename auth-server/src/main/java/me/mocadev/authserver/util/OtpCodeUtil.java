package me.mocadev.authserver.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-29
 **/
public class OtpCodeUtil {

	public static String generateOtpCode() {
		try {
			SecureRandom secureRandom = SecureRandom.getInstanceStrong();
			int value = secureRandom.nextInt(900000) + 100000;
			return String.valueOf(value);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
