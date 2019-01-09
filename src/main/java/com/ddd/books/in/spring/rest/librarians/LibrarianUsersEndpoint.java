package com.ddd.books.in.spring.rest.librarians;

import com.ddd.books.in.spring.func.users.User;
import com.ddd.books.in.spring.func.users.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/v1/librarians/users")
public class LibrarianUsersEndpoint {
    private final UsersService service;

    public LibrarianUsersEndpoint(final UsersService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = GET)
    public List<User> listAll(final @RequestParam(name = "name", required = false) String name) {
        return service.findAll(name);
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/{userId}", method = DELETE)
    public void deleteById(final @PathVariable("userId") UUID userId) {
        service.deleteById(userId);
    }
}
