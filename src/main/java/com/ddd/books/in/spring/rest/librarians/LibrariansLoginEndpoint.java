package com.ddd.books.in.spring.rest.librarians;

import com.ddd.books.in.spring.auth.AuthenticationService;
import com.ddd.books.in.spring.auth.LibrarianAuthentication;
import com.ddd.books.in.spring.auth.LibrarianLoginRequest;
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
@RequestMapping("/v1/librarians")
public class LibrariansLoginEndpoint {

    private final AuthenticationService service;

    public LibrariansLoginEndpoint(final AuthenticationService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/login", method = POST)
    public LibrarianAuthentication login(
            final HttpSession session,
            final @RequestBody @Valid LibrarianLoginRequest request) {
        final String username = request.getUsername();
        final String password = request.getPassword();
        final String sessionId = session.getId();

        return service.authenticateLibrarian(username, password, sessionId);
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/logout", method = DELETE)
    public void logout(final HttpSession session) {
        session.invalidate();
    }
}
