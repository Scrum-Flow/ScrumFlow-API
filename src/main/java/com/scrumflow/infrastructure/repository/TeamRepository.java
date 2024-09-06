package com.scrumflow.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrumflow.domain.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {}
