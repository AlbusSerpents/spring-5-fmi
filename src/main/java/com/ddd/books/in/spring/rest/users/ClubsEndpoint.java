package com.ddd.books.in.spring.rest.users;

import com.ddd.books.in.spring.auth.CustomUserDetails;
import com.ddd.books.in.spring.func.clubs.Club;
import com.ddd.books.in.spring.func.clubs.ClubInfo;
import com.ddd.books.in.spring.func.clubs.ClubsService;
import com.ddd.books.in.spring.func.clubs.CreateClubRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/users/clubs")
public class ClubsEndpoint {

    private final ClubsService service;

    public ClubsEndpoint(final ClubsService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "", method = POST)
    public Club create(
            final @RequestBody @Valid CreateClubRequest request,
            final @AuthenticationPrincipal CustomUserDetails details) {
        return service.create(request, details.getId(), details.getUser().getName());
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/{clubId}/join", method = POST)
    public void join(
            final @PathVariable("clubId") UUID clubId,
            final @AuthenticationPrincipal CustomUserDetails details) {
        service.join(clubId, details.getId(), details.getUser().getName());
    }
}
