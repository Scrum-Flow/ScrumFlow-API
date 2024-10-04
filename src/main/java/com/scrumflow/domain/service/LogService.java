package com.scrumflow.domain.service;

import org.springframework.stereotype.Service;

import com.scrumflow.domain.service.utilities.UserUtilities;
import com.scrumflow.infrastructure.model.Log;
import com.scrumflow.infrastructure.repository.LogRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final UserUtilities userUtilities;

    public void registerLog(String endpoint, String httpMethod, String login) {
        Log log =
                new Log(endpoint, httpMethod, login != null ? userUtilities.getUserByEmail(login) : null);

        logRepository.save(log);
    }
}
