package me.mocadev.authserver.controller.request;

import java.beans.ConstructorProperties;
import lombok.Getter;
import me.mocadev.authserver.annotation.PasswordEncryption;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-28
 **/
@Getter
public class EncryptedUserRequestBody {

	private final String userId;

	@PasswordEncryption
	private String password;

	@ConstructorProperties({"userId", "password"})
	public EncryptedUserRequestBody(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
}
