package com.scrumflow.application.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SprintRequestDTO(
        @Size(message = "O nome da print deve conter pelo menos 3 caracteres.", min = 3) String name,
        String description,
        @NotNull(message = "É necessário informar a data de início da sprint.") LocalDate startDate,
        @NotNull(message = "É necessário informar a data de término da sprint.") LocalDate endDate,
        @NotNull(message = "É necessário informar o ID do projeto.") Long projectId) {}
