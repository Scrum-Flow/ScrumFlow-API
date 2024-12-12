package com.scrumflow.mock;

import java.time.LocalDate;
import java.util.List;

import com.scrumflow.application.dto.request.ProjectKanbanColumnsRequestDTO;
import com.scrumflow.application.dto.request.ProjectRequestDTO;
import com.scrumflow.application.dto.response.ProjectDetailsResponseDTO;
import com.scrumflow.application.dto.response.ProjectDetailsResponseDTO.FeatureDetailsDTO;
import com.scrumflow.application.dto.response.ProjectDetailsResponseDTO.SprintDetailsDTO;
import com.scrumflow.application.dto.response.ProjectResponseDTO;
import com.scrumflow.domain.enums.TaskStatus;

public class ProjectMock {

    public static ProjectRequestDTO projectRequestMock() {
        return ProjectRequestDTO.builder()
                .name("Sample Project")
                .description("Sample Description")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .active(true)
                .build();
    }

    public static ProjectResponseDTO projectResponseMock() {
        return ProjectResponseDTO.builder()
                .id(1L)
                .name("Sample Project")
                .description("Sample Description")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .active(true)
                .build();
    }

    public static ProjectDetailsResponseDTO projectDetailsResponseMock() {
        return ProjectDetailsResponseDTO.builder()
                .id(1L)
                .name("Sample Project")
                .description("Detailed Description")
                .sprints(
                        List.of(
                                SprintDetailsDTO.builder()
                                        .id(1L)
                                        .name("Sprint 1")
                                        .description("First Sprint")
                                        .features(
                                                List.of(
                                                        FeatureDetailsDTO.builder()
                                                                .id(1L)
                                                                .name("Feature 1")
                                                                .description("Feature Description")
                                                                .tasks(List.of())
                                                                .build()))
                                        .build()))
                .kanbanColumns(List.of(TaskStatus.IN_PROGRESS, TaskStatus.IN_PROGRESS, TaskStatus.DONE))
                .build();
    }

    public static ProjectKanbanColumnsRequestDTO projectKanbanColumnsRequestMock() {
        return ProjectKanbanColumnsRequestDTO.builder()
                .columns(List.of(TaskStatus.IN_PROGRESS, TaskStatus.IN_PROGRESS, TaskStatus.DONE))
                .build();
    }
}
