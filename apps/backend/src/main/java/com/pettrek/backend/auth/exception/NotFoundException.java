package com.pettrek.backend.auth.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AuthException{
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
