package com.ddd.books.in.spring.rest.users;

import com.ddd.books.in.spring.auth.UserAuthentication;
import com.ddd.books.in.spring.func.users.RegistrationRequest;
import com.ddd.books.in.spring.func.users.UsersService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/v1/users")
public class UsersRegistrationEndpoint {
    private final UsersService service;

    public UsersRegistrationEndpoint(final UsersService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/register", method = POST)
    public UserAuthentication register(
            final HttpSession session,
            final @RequestBody @Valid RegistrationRequest request) {
        return service.register(request, session.getId());
    }
}
