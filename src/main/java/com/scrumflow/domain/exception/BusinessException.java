package com.scrumflow.domain.exception;

import java.util.List;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private List<String> errors;

    public BusinessException(List<String> errors) {
        super("Erros de validação");
        this.errors = errors;
    }

    public BusinessException(String s) {
        super(s);
        this.errors = List.of(s);
    }

    public BusinessException(String s, Throwable e) {
        super(s, e);
        this.errors = List.of(s);
    }
}
