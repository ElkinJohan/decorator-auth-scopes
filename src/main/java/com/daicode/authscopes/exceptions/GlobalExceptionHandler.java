package com.daicode.authscopes.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<StandardApiExceptionResponse> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        var errorResponse = new StandardApiExceptionResponse(ex.getTitle(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<StandardApiExceptionResponse> handleBusinessRuleException(BusinessRuleException ex) {
        log.error("{} :: {}", ex.getTitle(), ex.getExceptionMessage());
        var errorResponse = new StandardApiExceptionResponse(ex.getTitle(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardApiExceptionResponse> handleUnknownHostException(Exception ex) {
        log.error("Unexpected error caused by: {}", ex.getMessage());
        var errorResponse = new StandardApiExceptionResponse("Unexpected error", "Verify error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardApiExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        String detailedMessage = String.join(", ", errorMessages);

        var errorResponse = new StandardApiExceptionResponse("Validation error", "Mandatory fields: ".concat(detailedMessage));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
