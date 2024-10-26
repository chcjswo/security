package me.mocadev.authserver.exception;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-27
 **/
public class InvalidAuthException extends RuntimeException {

	public InvalidAuthException() {
		super("사용자 ID 또는 비밀번호가 유효하지 않습니다.");
	}
}
