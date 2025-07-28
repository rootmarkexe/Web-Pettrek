package com.pettrek.backend.auth.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends AuthException{
    public ConflictException(String message){
        super(message, HttpStatus.CONFLICT);
    }
}
