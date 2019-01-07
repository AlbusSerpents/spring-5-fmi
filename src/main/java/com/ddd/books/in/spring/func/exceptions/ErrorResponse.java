package com.ddd.books.in.spring.func.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    public final ErrorCode code;
    public final String message;

    public enum ErrorCode {
        AUTHENTICATION_REQUIRED,
        REGISTRATION_FAILED
    }
}
