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

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final RedisTemplate<String, String> redisTemplate;

    public JwtTokenProvider(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateAccessToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() - refreshTokenExpirationMs);

        String refreshToken =  Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .claim("token_type","refresh")
                .compact();

        redisTemplate.opsForValue().set(
                "refresh:" + userPrincipal.getId(),
                refreshToken,
                refreshTokenExpirationMs,
                TimeUnit.MILLISECONDS
        );

        return refreshToken;
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
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
    
    
    public void validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        } catch (MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new JwtTokenInvalidException();
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenExpiredException();
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenUnsupportedException();
        }
    }
}
