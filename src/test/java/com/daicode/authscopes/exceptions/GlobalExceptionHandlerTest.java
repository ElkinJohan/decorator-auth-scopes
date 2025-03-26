package com.daicode.authscopes.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleUnauthorizedAccessException() {
        UnauthorizedAccessException ex = new UnauthorizedAccessException("Unauthorized rule violated", "Unauthorized rule error");

        ResponseEntity<StandardApiExceptionResponse> response = globalExceptionHandler.handleUnauthorizedAccessException(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Unauthorized rule violated", Objects.requireNonNull(response.getBody()).getTitle());
        assertEquals("Unauthorized rule error", response.getBody().getDetail());
    }

    @Test
    void testHandleUnknownHostException() {
        Exception ex = new Exception("Unexpected-error");

        ResponseEntity<StandardApiExceptionResponse> response = globalExceptionHandler.handleUnknownHostException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error", Objects.requireNonNull(response.getBody()).getTitle());
        assertEquals("Verify error", response.getBody().getDetail());
    }

    @Test
    void testHandleBusinessRuleException() {
        BusinessRuleException ex = new BusinessRuleException("Business rule violated", "Business rule error", HttpStatus.BAD_REQUEST, "");

        ResponseEntity<StandardApiExceptionResponse> response = globalExceptionHandler.handleBusinessRuleException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Business rule violated", Objects.requireNonNull(response.getBody()).getTitle());
        assertEquals("Business rule error", response.getBody().getDetail());
    }

    @Test
    void testHandleMethodArgumentNotValidException() {

        MethodArgumentNotValidException ex = getMethodArgumentNotValidException();

        // Calling the handler method
        ResponseEntity<StandardApiExceptionResponse> response = globalExceptionHandler.handleMethodArgumentNotValidException(ex);

        // Asserting the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation error", Objects.requireNonNull(response.getBody()).getTitle());
        assertEquals("Mandatory fields: clientId is mandatory, clientSecret is mandatory", response.getBody().getDetail());
    }

    private static MethodArgumentNotValidException getMethodArgumentNotValidException() {
        FieldError fieldError1 = new FieldError("objectName", "clientId", "clientId is mandatory");
        FieldError fieldError2 = new FieldError("objectName", "clientSecret", "clientSecret is mandatory");

        // Creating a BindingResult with the FieldErrors
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(fieldError1);
        bindingResult.addError(fieldError2);

        // Creating a real instance of MethodArgumentNotValidException
        return new MethodArgumentNotValidException(null, bindingResult);
    }
}