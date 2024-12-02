package com.scrumflow.application.dto.request;

import com.scrumflow.domain.enums.TaskStatus;

public record TaskStatusUpdateDTO(Long taskId, TaskStatus status) {}
