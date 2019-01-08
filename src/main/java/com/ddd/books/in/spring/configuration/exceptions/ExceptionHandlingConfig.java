package com.ddd.books.in.spring.configuration.exceptions;

import com.ddd.books.in.spring.func.exceptions.ErrorResponse;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.apache.commons.logging.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.*;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.logging.LogFactory.getLog;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ControllerAdvice
public class ExceptionHandlingConfig extends ResponseEntityExceptionHandler {

    private final Log log = getLog(this.getClass());

    @Override
    protected ResponseEntity<Object> handleBindException(
            final BindException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        final ErrorResponse response = handleBindingResult(ex);
        return new ResponseEntity<>(response, defaultHeaders(), BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        final BindingResult result = ex.getBindingResult();

        final ErrorResponse response = handleBindingResult(result);
        return new ResponseEntity<>(response, defaultHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler({ FunctionalException.class })
    protected ResponseEntity<Object> handleFunctional(
            final FunctionalException ex,
            final WebRequest request) {
        final ErrorResponse body = ex.getResponse();
        final HttpStatus status = ex.getStatus();

        log.error("Exception: " + body);
        return new ResponseEntity<>(body, defaultHeaders(), status);
    }

    @ExceptionHandler({ BadCredentialsException.class })
    protected ResponseEntity<Object> handleBadCredentials(
            final BadCredentialsException bce,
            final WebRequest request) {
        log.error("Bad Credentials: " + bce.getMessage());
        return new ResponseEntity<>(authenticationError(), defaultHeaders(), UNAUTHORIZED);
    }

    @ExceptionHandler({ RuntimeException.class })
    protected ResponseEntity<Object> handleRuntime(
            final RuntimeException re,
            final WebRequest request) {
        log.error("Runtime Exception: " + re);
        return new ResponseEntity<>(unknownError(), defaultHeaders(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<Object> handleNormalException(
            final Exception e,
            final WebRequest request) {
        log.error("Exception: " + e);
        return new ResponseEntity<>(unknownError(), defaultHeaders(), INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse handleBindingResult(final BindingResult result) {
        final String validationMessage = result
                .getFieldErrors()
                .stream()
                .map(ValidationExceptionDetails::fromFieldError)
                .map(ValidationExceptionDetails::toMessage)
                .collect(joining(lineSeparator()));

        log.error(validationMessage);
        return new ErrorResponse(REQUEST_VALIDATION_FAILED, "");
    }

    private ErrorResponse unknownError() {
        return new ErrorResponse(UNKNOWN_ERROR, null);
    }

    private ErrorResponse authenticationError() {
        return new ErrorResponse(AUTHENTICATION_FAILED, null);
    }

    private HttpHeaders defaultHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return headers;
    }
}
