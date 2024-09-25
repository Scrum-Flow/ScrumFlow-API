package com.scrumflow.application.dto.request;

import com.scrumflow.domain.enums.RoleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(
        @NotEmpty(message = "Necessário informar o nome do usuário") String name,
        @NotEmpty(message = "Necessário informar um email") String email,
        @NotEmpty(message = "Necessário informar a senha") String password,
        @NotNull(message = "É necessário informar a role do usuário") RoleType role) {}
