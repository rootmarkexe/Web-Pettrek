package com.pettrek.backend.auth.exception;

public class JwtTokenExpiredException extends UnauthorizedException{
    public JwtTokenExpiredException() {
        super("Срок действия токена истек");
    }
}
