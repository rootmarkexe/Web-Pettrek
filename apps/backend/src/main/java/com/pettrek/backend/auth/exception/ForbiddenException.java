package com.pettrek.backend.auth.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AuthException{
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
