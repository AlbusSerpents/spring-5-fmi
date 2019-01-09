package com.ddd.books.in.spring.rest.librarians;

import com.ddd.books.in.spring.func.clubs.ClubsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequestMapping("/v1/librarians/clubs")
public class LibrariansClubsEndpoint {

    private final ClubsService service;

    public LibrariansClubsEndpoint(final ClubsService service) {
        this.service = service;
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/{clubId}", method = DELETE)
    public void delete(final @PathVariable("clubId") UUID clubId) {
        service.delete(clubId);
    }
}
