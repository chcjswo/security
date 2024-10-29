package me.mocadev.authserver.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-30
 **/
class OtpCodeUtilTest {

	@Test
	@DisplayName("6자리 숫자값이 나와야 함")
	void test_1() {
		// given & when
		String otp = OtpCodeUtil.generateOtpCode();

		// then
		assertTrue(otp.chars().allMatch(Character::isDigit));

		assertEquals(6, otp.length());
	}
}
