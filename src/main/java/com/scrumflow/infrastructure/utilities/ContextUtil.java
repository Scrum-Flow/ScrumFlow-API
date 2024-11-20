package com.scrumflow.infrastructure.utilities;

import static java.util.Optional.ofNullable;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextUtil {

    public static User obterUsuarioLogado() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User)
                ofNullable(authentication)
                        .filter(Authentication::isAuthenticated)
                        .map(Authentication::getPrincipal)
                        .orElseThrow(() -> new AccessDeniedException("Usuário não autenticado"));
    }
}
