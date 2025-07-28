package com.pettrek.backend.auth.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AuthException{
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
