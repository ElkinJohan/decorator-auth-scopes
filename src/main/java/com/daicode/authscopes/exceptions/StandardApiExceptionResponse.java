package com.daicode.authscopes.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@NoArgsConstructor
@Getter
@Setter
public class StandardApiExceptionResponse {

    private String title;
    private String detail;
    private OffsetDateTime dateTime;

    public StandardApiExceptionResponse(String title, String detail) {
        super();
        this.title = title;
        this.detail = detail;
        this.dateTime = OffsetDateTime.now();
    }
}
