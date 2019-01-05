package com.ddd.books.in.spring.configuration.security;

import com.ddd.books.in.spring.func.exceptions.ErrorResponse;
import com.ddd.books.in.spring.configuration.Serialization.CustomObjectMapper;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.AUTHENTICATION_REQUIRED;
import static org.apache.commons.logging.LogFactory.getLog;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Primary
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private final Log logger = getLog(AuthenticationEntryPointImpl.class);

    private final CustomObjectMapper mapper;

    public AuthenticationEntryPointImpl(final CustomObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException) throws IOException {
        final String errorMessage = String.format(" Authentication error: %s", authException.getMessage());
        logger.warn(errorMessage);

        final String errorReason = "This request requires authentication";
        final ErrorResponse errorResponse = new ErrorResponse(AUTHENTICATION_REQUIRED, errorReason);

        response.setStatus(UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), errorResponse);
    }
}
