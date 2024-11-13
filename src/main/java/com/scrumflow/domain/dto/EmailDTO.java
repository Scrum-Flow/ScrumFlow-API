package com.scrumflow.domain.dto;

import com.scrumflow.domain.enums.TaskStatus;
import com.scrumflow.domain.model.User;

public record EmailDTO(TaskStatus oldStatus, TaskStatus newStatus, User user, String taskName) {}
