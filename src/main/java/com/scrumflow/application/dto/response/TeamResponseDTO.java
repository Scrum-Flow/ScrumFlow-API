package com.scrumflow.application.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

public record TeamResponseDTO(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ProjectResponseDTO project,
        Set<UserResponseDTO> users) {}
