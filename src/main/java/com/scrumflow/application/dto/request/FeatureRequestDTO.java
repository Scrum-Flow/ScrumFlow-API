package com.scrumflow.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record FeatureRequestDTO(
        @NotNull String name, String description, @NotNull Long projectId) {}
