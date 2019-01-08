package com.ddd.books.in.spring.func.exceptions;

import com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FunctionalException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorResponse response;

    public FunctionalException(
            final ErrorCode code,
            final String message,
            final HttpStatus status) {
        super();
        this.response = new ErrorResponse(code, message);
        this.status = status;
    }

    public FunctionalException(final ErrorCode code, final String message) {
        this(code, message, BAD_REQUEST);
    }
}
