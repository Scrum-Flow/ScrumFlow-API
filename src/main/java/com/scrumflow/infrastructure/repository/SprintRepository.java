package com.scrumflow.infrastructure.repository;

import com.scrumflow.domain.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}