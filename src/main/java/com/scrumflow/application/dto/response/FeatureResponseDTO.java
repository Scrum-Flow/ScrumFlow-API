package com.scrumflow.application.dto.response;

import java.util.List;

public record FeatureResponseDTO(Long id, String name, String description, List<Long> sprintsId) {}
