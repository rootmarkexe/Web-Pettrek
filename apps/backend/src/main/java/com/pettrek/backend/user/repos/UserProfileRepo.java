package com.pettrek.backend.user.repos;

import com.pettrek.backend.user.dto.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {
}
