package com.scrumflow.infrastructure.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.scrumflow.domain.model.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenService {
    @Value("${api.security.token_secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withIssuer("main-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(Algorithm.HMAC256(secret));
        } catch (JWTCreationException e) {
            log.error(String.valueOf(e));
            throw new AccessDeniedException("Unable to create JWT", e);
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("main-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            log.error(String.valueOf(e));
            return null;
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
