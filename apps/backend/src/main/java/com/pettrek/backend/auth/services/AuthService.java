package com.pettrek.backend.auth.services;

import com.pettrek.backend.auth.dto.AuthTokensResponse;
import com.pettrek.backend.auth.exception.*;
import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.auth.repos.UserRepo;
import com.pettrek.backend.auth.security.AuthUserDetailsService;
import com.pettrek.backend.auth.security.JwtTokenProvider;
import com.pettrek.backend.auth.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailService mailService;
    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    public AuthTokensResponse authenticateUser(String email, String password){
        User user = userRepo.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidCredentialsException();
        }

        try{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        return new AuthTokensResponse(
                accessToken,
                refreshToken,
                jwtTokenProvider.getAccessTokenExpirationInMs()
        );}catch(BadCredentialsException ex) {throw new InvalidCredentialsException();}
    }
    @Transactional
    public User registerUser(String email, String password, String name, String secondName, String surname, LocalDate dateOfBirth){
        if(userRepo.existsByEmail(email)){
            throw new EmailAlreadyExistsException();
        }

        User user = new User();

        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setSecondName(secondName);
        user.setSurname(surname);
        user.setDateOfBirth(dateOfBirth);
        user.setVerificationCode(UUID.randomUUID().toString());
        User result = userRepo.save(user);
        mailService.sendVerificationEmail(result);

        return result;
    }

    public AuthTokensResponse verifyEmail(String verificationCode){
        User user = userRepo.findByVerificationCode(verificationCode)
                .orElseThrow(InvalidCredentialsException::new);
        if (user.isEnabled()) {
            throw new AlreadyVerifiedException("Email уже подтвержден");
        }
        user.setEnabled(true);
        user.setVerificationCode(null);
        userRepo.save(user);
        System.out.println("CODE = " + verificationCode);
        UserDetails userDetails = authUserDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        return new AuthTokensResponse(
                accessToken,
                refreshToken,
                jwtTokenProvider.getAccessTokenExpirationInMs()
        );
    }

    public void requestPasswordReset(String email){
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь с email " + email + " не найден"));

        user.setResetPasswordToken(UUID.randomUUID().toString());
        userRepo.save(user);
        mailService.sendPasswordResetEmail(user);
    }

    public AuthTokensResponse resetPassword(String token, String newPassword) {
        User user = userRepo.findByResetPasswordToken(token)
                .orElseThrow(ResetPasswordTokenInvalidException::new);

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        userRepo.save(user);
        UserDetails userDetails = authUserDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        return new AuthTokensResponse(
                accessToken,
                refreshToken,
                jwtTokenProvider.getAccessTokenExpirationInMs()
        );
    }

    public AuthTokensResponse refreshToken(String refreshToken) {
        try{
        jwtTokenProvider.validateToken(refreshToken);}catch(JwtTokenExpiredException | JwtTokenInvalidException | JwtTokenUnsupportedException ex){
            throw new RefreshTokenInvalidException();
        }
        Long userId = jwtTokenProvider.getUserIdFromJWT(refreshToken);

        if (!jwtTokenProvider.validateRefreshToken(userId, refreshToken)) {
            throw new RefreshTokenInvalidException();
        }

        UserPrincipal userPrincipal = (UserPrincipal) authUserDetailsService.loadById(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());

        String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        return new AuthTokensResponse(newAccessToken, newRefreshToken,
                jwtTokenProvider.getAccessTokenExpirationInMs());
    }
    @Transactional
    public void logout(String refreshToken) {
        try{
        jwtTokenProvider.validateToken(refreshToken);} catch (JwtTokenExpiredException | JwtTokenInvalidException | JwtTokenUnsupportedException ex){
            throw new RefreshTokenInvalidException();
        }

        Long userId = jwtTokenProvider.getUserIdFromJWT(refreshToken);

        jwtTokenProvider.deleteRefreshToken(userId);

    }
}
