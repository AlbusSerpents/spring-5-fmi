package com.ddd.books.in.spring.rest;

import com.ddd.books.in.spring.auth.CustomUserDetails;
import com.ddd.books.in.spring.func.users.UpdateUserRequest;
import com.ddd.books.in.spring.func.users.User;
import com.ddd.books.in.spring.func.users.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/v1/users/profile")
public class UserProfileEndpoint {

    private final UsersService service;

    public UserProfileEndpoint(final UsersService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{userId}", method = GET)
    public Object getProfile(final @AuthenticationPrincipal CustomUserDetails details) {
        return service.readById(details.getId());
    }

    @RequestMapping(value = "/{userId}", method = PUT)
    public User updateProfile(
            final @RequestBody @Valid UpdateUserRequest request,
            final @AuthenticationPrincipal CustomUserDetails details) {
        return service.update(details.getId(), request);
    }
}
