package com.scrumflow.infrastructure.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrumflow.infrastructure.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
    Long deleteLogsByDateBefore(LocalDateTime date);
}
