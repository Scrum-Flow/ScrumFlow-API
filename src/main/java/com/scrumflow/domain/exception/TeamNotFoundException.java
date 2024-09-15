package com.scrumflow.domain.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long id) {
        super(String.format("Nenhum time com id %s foi encontrado.", id));
    }
}
