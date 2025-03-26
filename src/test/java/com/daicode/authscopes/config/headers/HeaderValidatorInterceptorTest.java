//package com.daicode.authscopes.config.headers;
//
//import com.daicode.authscopes.config.headers.validation.HeaderValidatorInterceptor;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.web.method.HandlerMethod;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class HeaderValidatorInterceptorTest {
//
//    @InjectMocks
//    private HeaderValidatorInterceptor interceptor;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private HandlerMethod handlerMethod;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @HeaderAuth(roles = {"USER"}, scopes = {"READ"})
//    public class TestClass {
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "USER, READ, true",
//            "ADMIN, READ, false",
//            "USER, WRITE, false",
//            "ADMIN, WRITE, false"
//    })
//    void testPreHandle_ParameterizedRolesAndScopes(String role, String scope, boolean expectedResult) {
//        HeaderAuth headerAuth = mock(HeaderAuth.class);
//        when(headerAuth.roles()).thenReturn(new String[]{"USER"});
//        when(headerAuth.scopes()).thenReturn(new String[]{"READ"});
//
//        when(handlerMethod.getMethodAnnotation(HeaderAuth.class)).thenReturn(headerAuth);
//        when(request.getHeader("X-Roles")).thenReturn(role);
//        when(request.getHeader("X-Scopes")).thenReturn(scope);
//
//        boolean result = interceptor.preHandle(request, response, handlerMethod);
//        assertEquals(result, expectedResult);
//        if (!expectedResult) {
//            verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
//    }
//
//    @Test
//    void testPreHandle_SuperAdminRole_ReturnsTrue() {
//        HeaderAuth headerAuth = mock(HeaderAuth.class);
//        when(handlerMethod.getMethodAnnotation(HeaderAuth.class)).thenReturn(headerAuth);
//        when(request.getHeader("X-Roles")).thenReturn("superadmin");
//        when(request.getHeader("X-Scopes")).thenReturn("ALL");
//
//        assertTrue(interceptor.preHandle(request, response, handlerMethod));
//    }
//
//    @Test
//    void testPreHandle_MissingRolesOrScopes_ReturnsFalse() {
//        HeaderAuth headerAuth = mock(HeaderAuth.class);
//        when(headerAuth.roles()).thenReturn(new String[]{"USER"});
//        when(headerAuth.scopes()).thenReturn(new String[]{"READ"});
//
//        when(handlerMethod.getMethodAnnotation(HeaderAuth.class)).thenReturn(headerAuth);
//        when(request.getHeader("X-Roles")).thenReturn(null);
//        when(request.getHeader("X-Scopes")).thenReturn("READ");
//
//        assertFalse(interceptor.preHandle(request, response, handlerMethod));
//        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
//    }
//
//    @Test
//    void testPreHandle_EmptyScopes_ReturnsFalse() {
//        HeaderAuth headerAuth = mock(HeaderAuth.class);
//        when(headerAuth.roles()).thenReturn(new String[]{"USER"});
//        when(headerAuth.scopes()).thenReturn(new String[]{"READ"});
//
//        when(handlerMethod.getMethodAnnotation(HeaderAuth.class)).thenReturn(headerAuth);
//        when(request.getHeader("X-Roles")).thenReturn("USER");
//        when(request.getHeader("X-Scopes")).thenReturn("");
//
//        assertFalse(interceptor.preHandle(request, response, handlerMethod));
//        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
//    }
//
//    @Test
//    void testPreHandle_nullScopes_ReturnsFalse() {
//        HeaderAuth headerAuth = mock(HeaderAuth.class);
//
//        when(handlerMethod.getMethodAnnotation(HeaderAuth.class)).thenReturn(headerAuth);
//        when(request.getHeader("X-Scopes")).thenReturn(null);
//
//        assertFalse(interceptor.preHandle(request, response, handlerMethod));
//        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
//    }
//
//    @Test
//    void testPreHandle_NoContentHeaders_permitAccess() {
//        boolean result = interceptor.preHandle(request, response, null);
//        assertTrue(result);
//    }
//
//    @Test
//    void testPreHandle_ClassLevelAuth_ReturnsTrue() throws NoSuchMethodException {
//        // Crear un HandlerMethod que refiera a nuestra clase de prueba
//        TestClass testInstance = new TestClass();
//        HandlerMethod handlerMethodClass = new HandlerMethod(testInstance, HeaderValidatorInterceptorTest.TestClass.class.getMethod("toString"));
//
//        // Simulando las cabeceras válidas
//        when(request.getHeader("X-Roles")).thenReturn("USER");
//        when(request.getHeader("X-Scopes")).thenReturn("READ");
//
//        // Asignamos el mock de handlerMethod
//        this.handlerMethod = handlerMethodClass;
//
//        boolean result = interceptor.preHandle(request, response, this.handlerMethod);
//
//        assertTrue(result);
//    }
//}
