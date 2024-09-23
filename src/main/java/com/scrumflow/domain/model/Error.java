package com.scrumflow.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Error {
    private List<String> errors = new ArrayList<String>();

    public void addError(String error) {
        errors.add(error);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String getErrorMessage() {
        return String.join("\n", errors);
    }
}
