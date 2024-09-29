package com.scrumflow.application.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RegisterRequestDTO(
        @NotEmpty(message = "Necessário informar o nome do usuário") String name,
        @NotEmpty(message = "Necessário informar um email") String email,
        @NotEmpty(message = "Necessário informar a senha") String password) {}
