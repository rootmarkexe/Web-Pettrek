package com.pettrek.backend.auth.repos;

import com.pettrek.backend.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByVerificationCode(String code);
    Optional<User> findByResetPasswordToken(String token);

}
