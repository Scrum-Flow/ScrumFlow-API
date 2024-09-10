package com.scrumflow.application.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TeamRequestDTO(
        @Size(min = 3, message = "O nome do time deve conter pelo menos 3 caracteres.") String name,
        @NotNull(message = "É necessário informar o ID do projeto") Long projectId,
        List<Long> teamMembers) {}
