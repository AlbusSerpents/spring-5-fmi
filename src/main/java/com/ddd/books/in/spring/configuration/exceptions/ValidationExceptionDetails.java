package com.ddd.books.in.spring.configuration.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
class ValidationExceptionDetails {
    private final String field;
    private final String message;

    static ValidationExceptionDetails fromFieldError(final FieldError error) {
        return new ValidationExceptionDetails(error.getField(), error.getDefaultMessage());
    }

    String toMessage() {
        return field + ": " + message;
    }
}
