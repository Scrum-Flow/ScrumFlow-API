package com.scrumflow.domain.model;

import java.time.LocalDateTime;

import com.scrumflow.domain.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task_history")
@Getter
@Setter
@NoArgsConstructor
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus toStatus;

    @Column(
            name = "moved_at",
            updatable = false,
            insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime movedAt;

    public TaskHistory(Task task, User user, TaskStatus fromStatus, TaskStatus toStatus) {
        this.task = task;
        this.user = user;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
    }
}
