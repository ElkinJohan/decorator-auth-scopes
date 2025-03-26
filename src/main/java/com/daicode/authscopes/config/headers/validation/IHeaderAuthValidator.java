package com.daicode.authscopes.config.headers.validation;

import com.daicode.authscopes.config.headers.HeaderAuth;
import jakarta.servlet.http.HttpServletRequest;

public interface IHeaderAuthValidator {
    boolean isAuthorized(HttpServletRequest request, HeaderAuth headerAuth);
}
