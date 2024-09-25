package com.scrumflow.application.dto.response;

import java.util.List;

public record LoginResponseDTO(String name, String email, String token, List<RoleDTO> role) {}
