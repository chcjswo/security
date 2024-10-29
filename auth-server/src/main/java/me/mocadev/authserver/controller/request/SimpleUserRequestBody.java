package me.mocadev.authserver.controller.request;

import java.beans.ConstructorProperties;
import lombok.Getter;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-29
 **/
@Getter
public class SimpleUserRequestBody {

	private final String userId;
	private String password;

	@ConstructorProperties({"userId", "password"})
	public SimpleUserRequestBody(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
}
