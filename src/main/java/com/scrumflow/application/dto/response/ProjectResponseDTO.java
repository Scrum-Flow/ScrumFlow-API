package com.scrumflow.application.dto.response;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record ProjectResponseDTO(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Boolean active) {}
