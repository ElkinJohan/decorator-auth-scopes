package com.daicode.authscopes.config.headers.validation;

import com.daicode.authscopes.config.headers.HeaderAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class HeaderValidatorInterceptor implements HandlerInterceptor {

    private final IHeaderAuthValidator headerAuthValidator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (handler instanceof HandlerMethod handlerMethod) {
            HeaderAuth headerAuth = getHeaderAuth(handlerMethod);
            if (headerAuth != null && !headerAuthValidator.isAuthorized(request, headerAuth)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }
        return true;
    }

    private HeaderAuth getHeaderAuth(HandlerMethod handlerMethod) {
        HeaderAuth methodAnnotation = handlerMethod.getMethodAnnotation(HeaderAuth.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }
        return handlerMethod.getBeanType().getAnnotation(HeaderAuth.class);
    }
}
