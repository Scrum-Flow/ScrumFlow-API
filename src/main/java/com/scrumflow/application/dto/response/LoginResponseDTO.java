package com.scrumflow.application.dto.response;

public record LoginResponseDTO(UserResponseDTO user, String token) {}
