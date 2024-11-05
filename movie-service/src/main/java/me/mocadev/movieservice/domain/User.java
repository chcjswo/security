package me.mocadev.movieservice.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-11-05
 **/
@Getter
@Builder
public class User {
	private final String userId;
	private final String password;
	private final String otp;
}
