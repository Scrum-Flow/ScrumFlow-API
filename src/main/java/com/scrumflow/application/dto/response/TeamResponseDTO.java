package com.scrumflow.application.dto.response;

import java.util.List;

import com.scrumflow.domain.model.User;

public record TeamResponseDTO(Long id, String name, Long projectId, List<User> teamMembers) {}
