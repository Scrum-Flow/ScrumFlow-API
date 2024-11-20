package com.scrumflow.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scrumflow.domain.model.TaskHistory;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {

    List<TaskHistory> findAllByTaskId(Long taskId);
}
