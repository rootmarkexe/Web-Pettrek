package com.pettrek.backend.passport.repos;

import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.passport.models.Pet;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PassportRepo extends JpaRepository<User, Long> {
    @Query("SELECT user FROM User user LEFT JOIN FETCH user.pets WHERE user.id = :userId")
    Optional<User> findUserWithPets(@Param("userId") Long userId);
}
