package com.scrumflow.domain.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.scrumflow.infrastructure.repository.LogRepository;
import com.scrumflow.infrastructure.utilities.LogRetentionPolicy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogCleanupService {
    private final LogRepository logRepository;
    private final LogRetentionPolicy logRetentionPolicy;

    @Transactional
    @Scheduled(fixedRateString = "${api.log.cleanup.job-interval}")
    public void cleanupOldLogs() {
        int retentionPolicy = logRetentionPolicy.getCleanupRetentionPeriod();

        if (retentionPolicy > 0) {
            LocalDateTime cutOffDate = LocalDateTime.now().minusDays(retentionPolicy);

            try {
                logRepository.deleteLogsByDateBefore(cutOffDate);
            } catch (Exception e) {
                throw new RuntimeException("Error deleting old logs", e);
            }
        }
    }
}
