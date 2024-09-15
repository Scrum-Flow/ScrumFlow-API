package com.scrumflow.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrumflow.domain.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByNameContaining(String name);

    List<Team> findByProjectId(Long projectId);
}
