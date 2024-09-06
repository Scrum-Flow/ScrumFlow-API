package com.scrumflow.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String s) {
        super(s);
    }

    public InvalidCredentialsException(String s, Throwable e) {
        super(s, e);
    }
}
