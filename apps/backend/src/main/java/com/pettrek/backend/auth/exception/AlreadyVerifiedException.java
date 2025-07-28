package com.pettrek.backend.auth.exception;

public class AlreadyVerifiedException extends NotFoundException{
    public AlreadyVerifiedException(String message) {
        super(message);
    }
}
