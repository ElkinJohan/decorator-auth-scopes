package com.daicode.authscopes.config.headers.validation;

import com.daicode.authscopes.config.headers.HeaderAuth;
import com.daicode.authscopes.exceptions.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class HeaderAuthValidatorImplTest {

    @InjectMocks
    private HeaderAuthValidatorImpl headerAuthValidator;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        headerAuthValidator.setPrivilegedRole("ADMIN");
    }

    @Test
    void testAuthorizedWithPrivilegedRole() {
        when(request.getHeader("X-Roles")).thenReturn("ADMIN");
        assertTrue(headerAuthValidator.isAuthorized(request, createHeaderAuth()));
    }

    @Test
    void testAuthorizedWithValidScope() {
        when(request.getHeader("X-Scopes")).thenReturn("READ");
        assertTrue(headerAuthValidator.isAuthorized(request, createHeaderAuth("READ")));
    }

    @Test
    void testUnauthorizedWhenNoScopesProvided() {
        when(request.getHeader("X-Scopes")).thenReturn(null);
        Supplier<Object> supplier = () -> headerAuthValidator.isAuthorized(request, createHeaderAuth("WRITE"));
        assertThrows(UnauthorizedAccessException.class, supplier::get);
    }

    @Test
    void testUnauthorizedWithInvalidScope() {
        when(request.getHeader("X-Scopes")).thenReturn("READ");
        Supplier<Object> supplier = () -> headerAuthValidator.isAuthorized(request, createHeaderAuth("WRITE"));
        assertThrows(UnauthorizedAccessException.class, supplier::get);
    }

    private HeaderAuth createHeaderAuth(String... scopes) {
        return new HeaderAuth() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return HeaderAuth.class;
            }

            @Override
            public String[] roles() {
                return new String[0];
            }

            @Override
            public String[] scopes() {
                return scopes;
            }
        };
    }
}