package com.scrumflow.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrumflow.domain.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {}
