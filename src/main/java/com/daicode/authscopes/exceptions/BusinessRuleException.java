package com.daicode.authscopes.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BusinessRuleException extends RuntimeException {

    private final String title;
    private final HttpStatus httpStatus;
    private final String exceptionMessage;

    public BusinessRuleException(String title, String customDetail, HttpStatus httpStatus, String exceptionMessage) {
        super(customDetail);
        this.title = title;
        this.httpStatus = httpStatus;
        this.exceptionMessage = exceptionMessage;
    }
}
