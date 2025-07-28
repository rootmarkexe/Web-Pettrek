package com.pettrek.backend.auth.exception;

public class JwtTokenInvalidException extends UnauthorizedException{
    public JwtTokenInvalidException() {
        super("Токен недействителен");
    }
}
