package me.mocadev.authserver.controller.request;

import java.beans.ConstructorProperties;
import lombok.Getter;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-29
 **/
@Getter
public class SimpleOtpRequestBody {
	private final String userId;
	private final String otp;

	@ConstructorProperties({"userId", "otp"})
	public SimpleOtpRequestBody(String userId, String otp) {
		this.userId = userId;
		this.otp = otp;
	}
}
