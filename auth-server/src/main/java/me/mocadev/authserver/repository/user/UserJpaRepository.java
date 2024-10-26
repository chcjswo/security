package me.mocadev.authserver.repository.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import me.mocadev.authserver.entity.user.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

	Optional<UserEntity> findUserEntityByUserId(String userId);
}
