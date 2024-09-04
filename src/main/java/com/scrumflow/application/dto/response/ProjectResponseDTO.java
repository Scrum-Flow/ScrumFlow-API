package com.scrumflow.application.dto.response;

import java.time.LocalDate;

public record ProjectResponseDTO(
        Long id, String name, String description, LocalDate startDate, LocalDate endDate) {}
