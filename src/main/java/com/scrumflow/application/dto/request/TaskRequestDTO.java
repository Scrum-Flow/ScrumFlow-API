package com.scrumflow.application.dto.request;

import com.scrumflow.domain.enums.TaskStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskRequestDTO(
        @NotNull @Size(max = 255) String name,
        String description,
        @Min(0) Integer estimatePoints,
        @NotNull Long featureId,
        Long assignedToUserId,
        TaskStatus status) {}
