package com.pettrek.backend.auth.exception;

public class RefreshTokenInvalidException extends UnauthorizedException{

    public RefreshTokenInvalidException() {
        super("Неверный refresh токен");
    }
}
