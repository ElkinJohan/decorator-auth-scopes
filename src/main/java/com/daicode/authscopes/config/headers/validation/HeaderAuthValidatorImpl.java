package com.daicode.authscopes.config.headers.validation;

import com.daicode.authscopes.config.headers.HeaderAuth;
import com.daicode.authscopes.exceptions.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Setter
@Getter
@Slf4j
@Service
public class HeaderAuthValidatorImpl implements IHeaderAuthValidator {

    @Value("${privileged.role}")
    private String privilegedRole;

    @Override
    public boolean isAuthorized(HttpServletRequest request, HeaderAuth headerAuth) {
        String roles = request.getHeader("X-Roles");
        String scopes = request.getHeader("X-Scopes");

        String[] rolesArray = roles != null ? roles.trim().split("\\s+") : new String[0];
        String[] scopesArray = scopes != null ? scopes.trim().split("\\s+") : new String[0];

        if (Arrays.asList(rolesArray).contains(getPrivilegedRole())) {
            return true;
        }

        if (scopesArray.length == 0) {
            log.error("Access denied for URI '{}': Scopes not provided in the request", request.getRequestURI());
            throw new UnauthorizedAccessException(
                    "Access denied by scopes", "Scopes not provided in the request");
        }

        boolean validRoles =
                (headerAuth.roles() == null ||
                        Arrays.stream(headerAuth.roles()).allMatch(String::isBlank)) ||
                        Arrays.stream(headerAuth.roles()).anyMatch(role -> Arrays.asList(rolesArray).contains(role));

        boolean validScopes = Arrays.stream(headerAuth.scopes()).anyMatch(scope -> Arrays.asList(scopesArray).contains(scope));

        if (!validRoles || !validScopes) {
            log.error("Access denied for URI '{}': Roles or scopes not allowed. Required Roles: {}, Provided: {} | Required Scopes: {}, Provided: {}",
                    request.getRequestURI(), Arrays.toString(headerAuth.roles()), Arrays.toString(rolesArray),
                    Arrays.toString(headerAuth.scopes()), Arrays.toString(scopesArray));
            throw new UnauthorizedAccessException(
                    "Access denied by roles or scopes",
                    "Roles or scopes not allowed for the process");
        }

        return true;
    }
}
