package com.scrumflow.application.dto.response;

import java.time.LocalDateTime;

import com.scrumflow.domain.enums.TaskStatus;

public record TaskHistoryResponseDTO(
        Long id,
        Long taskId,
        String userName,
        TaskStatus fromStatus,
        TaskStatus toStatus,
        LocalDateTime movedAt) {}
