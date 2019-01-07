package com.ddd.books.in.spring.func.exceptions;

import com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ResponseStatus(BAD_REQUEST)
public class FunctionalException extends RuntimeException {
    private final ErrorResponse response;

    public FunctionalException(final ErrorCode code, final String message) {
        super();
        this.response = new ErrorResponse(code, message);
    }
}
