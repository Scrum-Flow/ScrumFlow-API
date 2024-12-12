package com.scrumflow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scrumflow.domain.service.LogService;
import org.mockito.Mockito;

@Configuration
public class TestMockConfig {

    @Bean
    public LogService logService() {
        return Mockito.mock(LogService.class);
    }
}
