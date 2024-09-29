package com.scrumflow.application.dto.response;

import java.time.LocalDateTime;

public record TaskResponseDTO(
        Long id,
        String name,
        String description,
        Integer estimatePoints,
        String featureName,
        String assignedToUserName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {}
