package com.pettrek.backend.auth.exception;

public class EmailAlreadyExistsException extends ConflictException{
    public EmailAlreadyExistsException(){
        super("Такой email уже существует");
    }
}
