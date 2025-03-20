package com.daicode.authscopes.config.headers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Slf4j
@Component
public class HeaderValidatorInterceptor implements HandlerInterceptor {
    private static final String SUPER_ADMIN_ROLE = "superadmin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (handler instanceof HandlerMethod handlerMethod) {
            HeaderAuth methodHeaderAuth = handlerMethod.getMethodAnnotation(HeaderAuth.class);
            if (methodHeaderAuth != null) {
                return validateHeaders(request, response, methodHeaderAuth);
            }

            HeaderAuth classHeaderAuth = handlerMethod.getBeanType().getAnnotation(HeaderAuth.class);
            if (classHeaderAuth != null) {
                return validateHeaders(request, response, classHeaderAuth);
            }
        }

        return true;
    }

    private boolean validateHeaders(HttpServletRequest request, HttpServletResponse response, HeaderAuth headerAuth) {
        String roles = request.getHeader("X-Roles");
        String scopes = request.getHeader("X-Scopes");

        String[] rolesArray = roles != null ? roles.trim().split("\\s+") : new String[0];
        String[] scopesArray = scopes != null ? scopes.trim().split("\\s+") : new String[0];

        if (Arrays.asList(rolesArray).contains(SUPER_ADMIN_ROLE)) {
            return true;
        }

        if (scopesArray.length == 0) {
            log.error("Access denied: Roles or scopes not provided in the request");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        boolean validRoles =
                (headerAuth.roles() == null ||
                        Arrays.stream(headerAuth.roles()).allMatch(String::isBlank)) ||
                        Arrays.stream(headerAuth.roles()).anyMatch(role -> Arrays.asList(rolesArray).contains(role));

        boolean validScopes = Arrays.stream(headerAuth.scopes()).anyMatch(scope -> Arrays.asList(scopesArray).contains(scope));

        if (!validRoles || !validScopes) {
            log.error("Access denied: Roles or scopes not allowed for the process");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }
}