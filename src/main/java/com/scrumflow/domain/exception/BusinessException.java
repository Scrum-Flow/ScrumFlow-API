package com.scrumflow.domain.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String s) {
        super(s);
    }

    public BusinessException(String s, Throwable e) {
        super(s, e);
    }
}
