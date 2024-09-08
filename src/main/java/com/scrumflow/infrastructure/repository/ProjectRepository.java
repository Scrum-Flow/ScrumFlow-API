package com.scrumflow.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrumflow.domain.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
