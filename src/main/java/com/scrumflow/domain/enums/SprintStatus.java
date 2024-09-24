package com.scrumflow.domain.enums;

public enum SprintStatus {
    PLANEJADA,
    EM_ANDAMENTO,
    CONCLUIDA;

    @Override
    public String toString() {
        return name();
    }
}
