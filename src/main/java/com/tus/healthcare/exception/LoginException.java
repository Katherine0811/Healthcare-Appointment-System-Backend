package com.tus.healthcare.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}