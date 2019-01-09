package com.ddd.books.in.spring.rest.commons;

import com.ddd.books.in.spring.func.clubs.Club;
import com.ddd.books.in.spring.func.clubs.ClubInfo;
import com.ddd.books.in.spring.func.clubs.ClubsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/v1/common/clubs")
public class CommonsClubsEndpoint {

    private final ClubsService service;

    public CommonsClubsEndpoint(final ClubsService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = GET)
    public List<ClubInfo> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{clubId}", method = GET)
    public Club getById(final @PathVariable("clubId") UUID clubId) {
        return service.readById(clubId);
    }
}
