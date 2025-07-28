package com.pettrek.backend.auth.exception;

public class InvalidVerificationCodeException extends BadRequestException{
    public InvalidVerificationCodeException() {
        super("Неверный код подтверждения");
    }
}
