package me.mocadev.authserver.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import me.mocadev.authserver.controller.request.EncryptedUserRequestBody;
import me.mocadev.authserver.domain.user.User;
import me.mocadev.authserver.service.UserService;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-27
 **/
@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/api/v1/users")
	public User createNewUser(@RequestBody EncryptedUserRequestBody requestBody) {
		return userService.createNewUser(requestBody.getUserId(), requestBody.getPassword());
	}
}
