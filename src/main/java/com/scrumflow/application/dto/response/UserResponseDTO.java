package com.scrumflow.application.dto.response;

import java.util.List;

public record UserResponseDTO(
        Long id, String name, String email, boolean active, List<RoleResponseDTO> roles) {}
