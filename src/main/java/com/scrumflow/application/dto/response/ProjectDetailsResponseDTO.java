package com.scrumflow.application.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record ProjectDetailsResponseDTO(
        Long id, String name, String description, List<SprintDetailsDTO> sprints) {
    @Builder
    public record SprintDetailsDTO(
            Long id, String name, String description, List<FeatureDetailsDTO> features) {}

    @Builder
    public record FeatureDetailsDTO(
            Long id, String name, String description, List<TaskResponseDTO> tasks) {}
}
