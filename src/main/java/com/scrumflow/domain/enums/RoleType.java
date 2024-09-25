package com.scrumflow.domain.enums;

public enum RoleType {
    ADMIN,
    PROJECT_MANAGER,
    PRODUCT_OWNER;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
