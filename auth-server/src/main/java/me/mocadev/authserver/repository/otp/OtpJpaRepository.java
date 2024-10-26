package me.mocadev.authserver.repository.otp;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import me.mocadev.authserver.entity.otp.OtpEntity;

public interface OtpJpaRepository extends JpaRepository<OtpEntity, Integer> {

	Optional<OtpEntity> findOtpEntityByUserId(String userId);
}
