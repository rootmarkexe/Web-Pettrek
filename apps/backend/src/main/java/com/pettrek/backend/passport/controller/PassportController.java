package com.pettrek.backend.passport;

import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.auth.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.pettrek.backend.passport.services.PassportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("api/passport")
public class PassportController {
    private final PassportService passportService;
    private static final Logger logger = LoggerFactory.getLogger(PassportController.class);
    public PassportController(PassportService passportService){
        this.passportService = passportService;
    }

    @GetMapping("/passport")
    public ResponseEntity<?> getPassport(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Получен authentication из SecurityContextHolder");
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        logger.info("Principal class: {}", authentication.getPrincipal().getClass());
        logger.info("Получен пользователь из authentication");
        logger.info("User ID: {}", userPrincipal.getId());
        User userWithPets = passportService.getUserWithPets(userPrincipal.getId());
        logger.info("Получен userWithPets");
        return ResponseEntity.ok(userWithPets);
    }

//    @PostMapping("/create-passport")
//    public ResponseEntity<?> createPassport(){
//
//
//    }
}
