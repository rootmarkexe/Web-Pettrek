package com.pettrek.backend.auth.security;

import com.pettrek.backend.auth.exception.JwtTokenExpiredException;
import com.pettrek.backend.auth.exception.JwtTokenInvalidException;
import com.pettrek.backend.auth.exception.JwtTokenUnsupportedException;
import com.pettrek.backend.auth.exception.RefreshTokenInvalidException;
import io.jsonwebtoken.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Getter

public class JwtTokenProvider {

    @Value("${application.jwtSecret}")
    private String jwtSecret;

    @Value("${application.accessTokenExpirationInMs}")
    private Long accessTokenExpirationInMs;

    @Value("${application.refreshTokenExpirationInMs}")
    private Long refreshTokenExpirationMs;
    private final SecretKey key;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final RedisTemplate<String, String> redisTemplate;

    public JwtTokenProvider(
            RedisTemplate<String, String> redisTemplate,
            @Value("${application.jwtSecret}") String jwtSecret) {

        this.redisTemplate = redisTemplate;
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateAccessToken(Authentication authentication){
        logger.trace("Start token generation");
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        logger.trace("Principal obtained");
        Date now = new Date();
        logger.trace("Current date: {}", now);
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationInMs);
        logger.trace("Expiry date: {}", expiryDate);

        logger.trace("Building JWT...");
        String token = Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        logger.trace("Token generated successfully");
        return token;
    }

    public String generateRefreshToken(Authentication authentication){
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + refreshTokenExpirationMs);

            String refreshToken = Jwts.builder()
                    .setSubject(Long.toString(userPrincipal.getId()))
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .claim("token_type", "refresh")
                    .compact();

//            redisTemplate.opsForValue().set(
//                    "refresh:" + userPrincipal.getId(),
//                    refreshToken,
//                    refreshTokenExpirationMs,
//                    TimeUnit.MILLISECONDS
//            );
            logger.debug("Saved refresh token in Redis: {}");

        return refreshToken;}catch (Exception e) {
            logger.error("Refresh token generation failed", e);
            return "dcsdsdf";
        }
    }
    public boolean validateRefreshToken(Long userId, String refreshToken) {
        String storedToken = redisTemplate.opsForValue().get("refresh:" + userId);
        try{
        return refreshToken.equals(storedToken);}catch(JwtTokenExpiredException | JwtTokenInvalidException | JwtTokenUnsupportedException ex){
            throw new RefreshTokenInvalidException();
        }
    }

    public void deleteRefreshToken(Long userId) {
        redisTemplate.delete("refresh:" + userId);
    }

    public Long getUserIdFromJWT(String token){
        return Long.parseLong(
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }

    public void validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
        } catch (MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new JwtTokenInvalidException();
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenExpiredException();
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenUnsupportedException();
        }
    }
}
