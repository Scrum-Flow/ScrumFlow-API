package com.scrumflow.application.dto.request;

import java.util.List;

import com.scrumflow.domain.enums.TaskStatus;

public record ProjectKanbanColumnsRequestDTO(List<TaskStatus> columns) {}
