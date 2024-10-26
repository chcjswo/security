package me.mocadev.authserver.repository.auth;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionOperations;
import lombok.RequiredArgsConstructor;
import me.mocadev.authserver.domain.user.User;
import me.mocadev.authserver.entity.otp.OtpEntity;
import me.mocadev.authserver.entity.user.UserEntity;
import me.mocadev.authserver.exception.InvalidAuthException;
import me.mocadev.authserver.repository.otp.OtpJpaRepository;
import me.mocadev.authserver.repository.user.UserJpaRepository;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-27
 **/
@Repository
@RequiredArgsConstructor
public class AuthRepository {
	private final OtpJpaRepository otpJpaRepository;
	private final UserJpaRepository userJpaRepository;

	private final TransactionOperations readTransactionOperations;
	private final TransactionOperations writeTransactionOperations;

	public User createNewUser(User user) {
		return writeTransactionOperations.execute(status -> {
			Optional<UserEntity> userOptional = userJpaRepository.findUserEntityByUserId(user.getUserId());
			if (userOptional.isPresent()) {
				throw new RuntimeException(String.format("User [%s] already exists", user.getUserId()));
			}

			UserEntity saved = userJpaRepository.save(user.toEntity());
			return saved.toDomain();
		});
	}

	public User getUserByUserId(String userId) {
		return readTransactionOperations.execute(status ->
			userJpaRepository.findUserEntityByUserId(userId)
				.orElseThrow(InvalidAuthException::new)
				.toDomain());
	}

	public String getOtp(String userId) {
		return readTransactionOperations.execute(status -> otpJpaRepository.findOtpEntityByUserId(userId)
			.orElseThrow(() -> new RuntimeException(String.format("User [%s] does not exist", userId)))
			.getOtpCode());
	}

	public void upsertOtp(String userId, String newOtp) {
		writeTransactionOperations.executeWithoutResult(status -> {
			Optional<OtpEntity> optOptional = otpJpaRepository.findOtpEntityByUserId(userId);

			if (optOptional.isPresent()) {
				optOptional.get().renewOtp(newOtp);
			} else {
				otpJpaRepository.save(new OtpEntity(userId, newOtp));
			}
		});
	}
}
