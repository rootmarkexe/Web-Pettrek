package com.pettrek.backend.passport.services;

import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.auth.repos.UserRepo;
import com.pettrek.backend.auth.security.UserPrincipal;
import com.pettrek.backend.passport.repos.PassportRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContextService {

    private final UserRepo userRepo;
    private final PassportRepo passportRepo;

    public User getCurrentUser() {
        UserPrincipal principal = getCurrentUserPrincipal();
        return userRepo.findById(principal.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    public User getCurrentUserWithPets() {
        UserPrincipal principal = getCurrentUserPrincipal();
        return passportRepo.findUserWithPets(principal.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с питомцами не найден"));
    }

    private UserPrincipal getCurrentUserPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal) authentication.getPrincipal();
    }
}
