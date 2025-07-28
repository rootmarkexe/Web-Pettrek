package com.pettrek.backend.auth.exception;

public class ResetPasswordTokenInvalidException extends NotFoundException{
    public ResetPasswordTokenInvalidException() {
        super("Неверный токен сброса пароля");
    }
}
