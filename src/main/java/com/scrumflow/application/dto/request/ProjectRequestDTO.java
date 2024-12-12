package com.scrumflow.application.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProjectRequestDTO(
        @NotNull String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        @NotNull Boolean active) {}
