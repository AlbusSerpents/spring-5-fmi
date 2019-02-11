package com.ddd.books.in.spring.rest.users;

import com.ddd.books.in.spring.func.events.EventsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/users/events")
public class UsersEventsEndpoint {

    private final EventsService service;

    public UsersEventsEndpoint(final EventsService service) { this.service = service; }

    @RequestMapping(value = "/{eventId}/subscribe", method = POST)
    public void subscribe(@PathVariable(value = "eventId") UUID eventId, Principal principal) {
        service.subscribe(eventId, principal);
    }

    @RequestMapping(value = "/{eventId}/unsubscribe", method = POST)
    public void unsubscribe(@PathVariable(value = "eventId") UUID eventId, Principal principal) {
        service.unsubscribe(eventId, principal);
    }
}
