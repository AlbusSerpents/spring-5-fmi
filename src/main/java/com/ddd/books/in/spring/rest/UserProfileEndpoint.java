package com.ddd.books.in.spring.rest;

import com.ddd.books.in.spring.auth.CustomUserDetails;
import com.ddd.books.in.spring.func.users.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/v1/profile")
public class UserProfileEndpoint {

    private final UsersService service;

    public UserProfileEndpoint(final UsersService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{userId}", method = GET)
    public Object getProfile(final @AuthenticationPrincipal CustomUserDetails details) {
        return service.readById(details.getId());
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/{userId}", method = PUT)
    public void updateProfile() {
    }
}
