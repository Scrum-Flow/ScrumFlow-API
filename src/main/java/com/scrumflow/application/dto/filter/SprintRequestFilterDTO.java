package com.scrumflow.application.dto.filter;

import java.time.LocalDate;

public record SprintRequestFilterDTO(Long projectId, LocalDate startDate, LocalDate endDate, String name) {
}
