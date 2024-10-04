package com.scrumflow.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrumflow.infrastructure.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {}
