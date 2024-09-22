package com.scrumflow.domain.enums;

public enum RoleType {
    ADMIN,
    MANAGER,
    USER;

    @Override
    public String toString() {
        return name();
    }
}
