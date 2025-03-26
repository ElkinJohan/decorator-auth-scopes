package com.daicode.authscopes.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorizedAccessException extends RuntimeException {

    private final String title;

    public UnauthorizedAccessException(String title, String customDetail) {
        super(customDetail);
        this.title = title;
    }
}
