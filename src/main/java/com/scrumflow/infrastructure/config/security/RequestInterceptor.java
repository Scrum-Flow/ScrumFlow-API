package com.scrumflow.infrastructure.config.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.scrumflow.domain.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {
    private final LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String route = request.getRequestURI();
        String method = request.getMethod();
        String login = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : null;

        logService.registerLog(route, method, login);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
