package com.pettrek.backend.user.services;

import com.pettrek.backend.user.dto.UserProfile;
import com.pettrek.backend.user.dto.UserProfileDto;
import com.pettrek.backend.user.repos.UserProfileRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {
    private final UserProfileRepo userProfileRepo;

    UserProfileService(UserProfileRepo userProfileRepo){
        this.userProfileRepo = userProfileRepo;
    }

    @Transactional
    public UserProfile initUserProfile(UserProfileDto userProfileDto){
        UserProfile userProfile = new UserProfile();
        userProfile.setName(userProfileDto.name());
        userProfile.setSecondName(userProfileDto.secondName());
        userProfile.setSurname(userProfileDto.surname());
        userProfile.setDateOfBirth(userProfileDto.dateOfBirth());
        userProfile.setCity(userProfileDto.city());
        UserProfile result = userProfileRepo.save(userProfile);
        return result;
    }
}
