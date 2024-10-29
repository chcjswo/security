package me.mocadev.authserver.repository.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionOperations;
import me.mocadev.authserver.domain.user.User;
import me.mocadev.authserver.entity.otp.OtpEntity;
import me.mocadev.authserver.entity.user.UserEntity;
import me.mocadev.authserver.repository.otp.OtpJpaRepository;
import me.mocadev.authserver.repository.user.UserJpaRepository;
import me.mocadev.authserver.util.OtpCodeUtil;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-30
 **/
@ExtendWith(MockitoExtension.class)
class AuthRepositoryTest {

	@Mock
	OtpJpaRepository otpJpaRepository;

	@Mock
	UserJpaRepository userJpaRepository;

	AuthRepository sut;

	@BeforeEach
	public void setup() {
		sut = new AuthRepository(
			otpJpaRepository,
			userJpaRepository,
			TransactionOperations.withoutTransaction(),
			TransactionOperations.withoutTransaction()
		);
	}

	@Test
	@DisplayName("동일한 사용자 ID 로 사용자를 등록할 수 없다.")
	void test_1() {
		// given
		String userId = "danny.kim";
		UserEntity userEntity = new UserEntity(
			userId, "password"
		);

		given(userJpaRepository.findUserEntityByUserId(userId))
			.willReturn(Optional.of(userEntity));

		// when & then
		assertThrows(RuntimeException.class,
			() -> sut.createNewUser(userEntity.toDomain())
		);
	}

	@Test
	@DisplayName("사용자를 등록할 수 있다.")
	void test_2() {
		// given
		String userId = "danny.kim";
		String password = "1234";
		given(userJpaRepository.findUserEntityByUserId(userId))
			.willReturn(Optional.empty());

		User user = new User(userId, password);
		given(userJpaRepository.save(any()))
			.willReturn(user.toEntity());

		// when
		User result = sut.createNewUser(user);

		// then
		verify(userJpaRepository, atMostOnce()).save(user.toEntity());
		assertEquals(result.getUserId(), userId);
		assertEquals(result.getPassword(), password);
	}

	@Test
	@DisplayName("사용자가 존재하면 OTP 값을 업데이트 한다.")
	void test_3() {
		// given
		String userId = "danny.kim";
		String otp = OtpCodeUtil.generateOtpCode();
		OtpEntity otpEntity = new OtpEntity();
		given(otpJpaRepository.findOtpEntityByUserId(userId))
			.willReturn(Optional.of(otpEntity));

		// when
		sut.upsertOtp(userId, otp);

		// then
		assertEquals(otp, otpEntity.getOtpCode());
	}

	@Test
	@DisplayName("사용자가 존재하지 않으면 새로운 OTP 를 생성한다.")
	void test_4() {
		// given
		String userId = "danny.kim";
		String otp = OtpCodeUtil.generateOtpCode();
		OtpEntity otpEntity = new OtpEntity();
		given(otpJpaRepository.findOtpEntityByUserId(userId))
			.willReturn(Optional.empty());

		// when
		sut.upsertOtp(userId, otp);

		// then
		verify(otpJpaRepository, atMostOnce()).save(any());
	}
}
