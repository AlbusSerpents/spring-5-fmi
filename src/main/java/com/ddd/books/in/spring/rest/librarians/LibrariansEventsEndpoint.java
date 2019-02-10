package com.ddd.books.in.spring.rest.librarians;

import com.ddd.books.in.spring.func.events.EventsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.DELETE;
import java.util.UUID;

@RestController
@RequestMapping("/v1/librarians/events")
public class LibrariansEventsEndpoint {
    private final EventsService service;
    public  LibrariansEventsEndpoint(EventsService service) {this.service = service;}

    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
    public void delete(final @PathVariable("eventId") UUID eventId) {
        service.delete(eventId);
    }


}
