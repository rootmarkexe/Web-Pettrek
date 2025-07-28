package com.pettrek.backend.auth.exception;

public class JwtTokenUnsupportedException extends BadRequestException{
    public JwtTokenUnsupportedException() {
        super("Неподдерживаемый токен");
    }
}
