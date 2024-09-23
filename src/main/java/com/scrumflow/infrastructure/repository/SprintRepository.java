package com.scrumflow.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrumflow.domain.model.Sprint;

public interface SprintRepository extends JpaRepository<Sprint, Long> {}
