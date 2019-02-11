package com.ddd.books.in.spring.rest.librarians;

import com.ddd.books.in.spring.func.events.Event;
import com.ddd.books.in.spring.func.events.EventsService;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/librarians/events")
public class LibrariansEventsEndpoint {
    private final EventsService service;
    public  LibrariansEventsEndpoint(EventsService service) {this.service = service;}

    @RequestMapping(value = "/add", method = POST)
    public Event createEvent(final @RequestBody Event event) {
        return service.create(event);
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
    public void delete(final @PathVariable("eventId") UUID eventId) {
        service.delete(eventId);
    }


}
