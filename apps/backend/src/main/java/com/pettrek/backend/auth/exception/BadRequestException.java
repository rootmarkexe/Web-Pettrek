package com.pettrek.backend.auth.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AuthException{
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
