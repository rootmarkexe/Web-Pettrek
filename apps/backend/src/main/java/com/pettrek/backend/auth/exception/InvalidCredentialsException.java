package com.pettrek.backend.auth.exception;

public class InvalidCredentialsException extends UnauthorizedException{
    public InvalidCredentialsException(){
        super("Неверный email или пароль");
    }
}
