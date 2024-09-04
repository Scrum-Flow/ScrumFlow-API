package com.scrumflow.infrastructure.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scrumflow.application.dto.response.ApiErrorMessage;
import com.scrumflow.domain.exception.BusinessException;
import com.scrumflow.domain.exception.NotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        final var apiErrorMessage =
                new ApiErrorMessage(HttpStatus.NOT_FOUND.value(), List.of(ex.getMessage()));
        return new ResponseEntity<>(apiErrorMessage, HttpStatus.valueOf(apiErrorMessage.getStatus()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        final var apiErrorMessage =
                new ApiErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), List.of(ex.getMessage()));
        return new ResponseEntity<>(apiErrorMessage, HttpStatus.valueOf(apiErrorMessage.getStatus()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        var apiErrorMessage =
                new ApiErrorMessage(HttpStatus.FORBIDDEN.value(), List.of(ex.getMessage()));
        return new ResponseEntity<>(apiErrorMessage, HttpStatus.valueOf(apiErrorMessage.getStatus()));
    }
}
