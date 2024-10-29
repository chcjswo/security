package me.mocadev.authserver.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import me.mocadev.authserver.controller.request.SimpleOtpRequestBody;
import me.mocadev.authserver.controller.request.SimpleUserRequestBody;
import me.mocadev.authserver.service.OtpService;
import me.mocadev.authserver.service.UserService;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-29
 **/
@RestController
@RequiredArgsConstructor
public class AuthController {

	private final OtpService otpService;
	private final UserService userService;

	@PostMapping("/api/v1/users/auth")
	public String auth(@RequestBody SimpleUserRequestBody requestBody) {
		return userService.auth(requestBody.getUserId(), requestBody.getPassword());
	}

	@PostMapping("/api/v1/otp/check")
	public boolean checkOtp(@RequestBody SimpleOtpRequestBody requestBody) {
		return otpService.checkOtp(requestBody.getUserId(), requestBody.getOtp());
	}
}
