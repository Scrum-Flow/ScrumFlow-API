package com.scrumflow.infrastructure.config.swagger;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class EnableSwaggerConfiguration {

    @Value("${info.app.name}")
    private String title;

    @Value("${info.app.description}")
    private String description;

    @Value("${server.port}")
    private String serverPort;

    EnableSwaggerConfiguration() {
        log.info("Enabled swagger configuration");
    }

    @Bean
    public OpenAPI customOpenAPI(BuildProperties buildProperties) {

        final var servers =
                Stream.of("http://localhost:" + serverPort).map(url -> new Server().url(url)).toList();

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .servers(servers)
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "Authorization",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")))
                .info(
                        new Info().title(title).version(buildProperties.getVersion()).description(description));
    }
}
