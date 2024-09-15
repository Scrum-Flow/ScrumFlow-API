package com.scrumflow.application.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.scrumflow.domain.model.Project;
import com.scrumflow.domain.model.User;

public record TeamResponseDTO(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Project project,
        Set<User> users) {}
