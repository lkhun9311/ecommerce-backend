package com.ecommerce.db.user;

import com.ecommerce.db.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByEmailAndPasswordAndStatusOrderByIdDesc(String email, String password, UserStatus status);

    Optional<UserEntity> findFirstByIdAndStatusOrderByIdDesc(Long userId, UserStatus status);
}
