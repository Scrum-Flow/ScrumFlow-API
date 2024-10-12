package com.scrumflow.infrastructure.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogRetentionPolicy {

    @Value("${api.log.cleanup.retention-period}")
    private int cleanupRetentionPeriod;

    public int getCleanupRetentionPeriod() {
        return cleanupRetentionPeriod <= 0 ? -1 : cleanupRetentionPeriod;
    }
}
