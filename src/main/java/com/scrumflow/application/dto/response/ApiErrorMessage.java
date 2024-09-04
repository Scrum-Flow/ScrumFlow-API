package com.scrumflow.application.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class ApiErrorMessage {

    private int status;
    private List<String> errors;

    public ApiErrorMessage(int status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }
}
