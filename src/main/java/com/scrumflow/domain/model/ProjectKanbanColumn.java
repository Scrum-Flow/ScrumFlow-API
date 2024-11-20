package com.scrumflow.domain.model;

import com.scrumflow.domain.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "project_kanban_columns",
        uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "column_name"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectKanbanColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "column_name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TaskStatus columnName;

    public void setProject(Project project) {
        if (project != null) {
            this.project = project;
            if (!project.getKanbanColumns().contains(this)) {
                project.getKanbanColumns().add(this);
            }
        }
    }
}
