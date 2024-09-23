package com.scrumflow.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.scrumflow.domain.enums.SprintStatus;

public record SprintResponseDTO(
        Long id,
        String name,
        String description,
        SprintStatus status,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long projectId) {}
