package com.daicode.authscopes.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
@Setter
public class UnauthorizedAccessException extends RuntimeException {

    private final String title;

    public UnauthorizedAccessException(String title, String customDetail) {
        super(customDetail);
        this.title = title;
    }
}
