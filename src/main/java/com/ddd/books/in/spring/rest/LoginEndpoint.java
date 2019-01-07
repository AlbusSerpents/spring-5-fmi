package com.ddd.books.in.spring.rest;

import com.ddd.books.in.spring.auth.AuthenticationService;
import com.ddd.books.in.spring.auth.LoginRequest;
import com.ddd.books.in.spring.auth.UserAuthentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/v1")
public class LoginEndpoint {

    private final AuthenticationService service;

    public LoginEndpoint(final AuthenticationService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/login", method = POST)
    public UserAuthentication login(
            final HttpSession session,
            final @RequestBody @Valid LoginRequest request) {
        final String username = request.getEmail();
        final String password = request.getPassword();
        final String sessionId = session.getId();

        return service.authenticateUser(username, password, sessionId);
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/logout", method = DELETE)
    public void logout(final HttpSession session) {
        session.invalidate();
    }
}
