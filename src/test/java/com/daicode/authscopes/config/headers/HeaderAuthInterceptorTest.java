package com.daicode.authscopes.config.headers;

import com.daicode.authscopes.config.headers.validation.IHeaderAuthValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.method.HandlerMethod;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class HeaderAuthInterceptorTest {

    @InjectMocks
    private HeaderAuthInterceptor interceptor;

    @Mock
    private IHeaderAuthValidator headerAuthValidator;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HandlerMethod handlerMethod;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInterceptorAllowsAuthorizedRequests() {
        when(handlerMethod.getMethodAnnotation(HeaderAuth.class)).thenReturn(createHeaderAuth("READ"));
        when(headerAuthValidator.isAuthorized(eq(request), any(HeaderAuth.class))).thenReturn(true);
        assertTrue(interceptor.preHandle(request, response, handlerMethod));
    }

    @Test
    void testInterceptorBlocksUnauthorizedRequests() {
        when(handlerMethod.getMethodAnnotation(HeaderAuth.class)).thenReturn(createHeaderAuth("WRITE"));
        when(headerAuthValidator.isAuthorized(eq(request), any(HeaderAuth.class))).thenReturn(false);
        when(response.getStatus()).thenReturn(HttpServletResponse.SC_FORBIDDEN);

        assertFalse(interceptor.preHandle(request, response, handlerMethod));
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

    @HeaderAuth(roles = {"USER"}, scopes = {"READ"})
    public class TestClass {
    }

    @Test
    void testPreHandle_ClassLevelAuth_ReturnsFalse() throws NoSuchMethodException {

        TestClass testInstance = new TestClass();
        HandlerMethod handlerMethodClass = new HandlerMethod(testInstance, HeaderAuthInterceptorTest.TestClass.class.getMethod("toString"));

        when(request.getHeader("X-Roles")).thenReturn("USER");
        when(request.getHeader("X-Scopes")).thenReturn("READ");

        this.handlerMethod = handlerMethodClass;

        boolean result = interceptor.preHandle(request, response, this.handlerMethod);

        assertFalse(result);
    }
}
