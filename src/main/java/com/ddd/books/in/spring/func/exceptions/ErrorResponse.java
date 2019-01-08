package com.ddd.books.in.spring.func.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    public final ErrorCode code;
    public final String message;

    public enum ErrorCode {
        // @TODO remove later on!
        TEST_CODE,

        UNKNOWN_ERROR,
        REQUEST_VALIDATION_FAILED,
        AUTHENTICATION_REQUIRED,
        AUTHENTICATION_FAILED,
        REGISTRATION_FAILED
    }
}
