package com.ddd.books.in.spring.rest.events;

import com.ddd.books.in.spring.func.events.Event;
import com.ddd.books.in.spring.func.events.EventsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/common/events")
public class EventsEndpoint {

    private final EventsService service;

    public EventsEndpoint(EventsService eventsService) {
        this.service = eventsService;
    }

    @RequestMapping(value = "/add", method = POST)
    public Event createEvent(final @RequestBody Event event) {
        return service.create(event);
    }

    @RequestMapping(value = "", method = GET)
    public List<Event> listAllEvents() {
        return service.listAll();
    }

    @RequestMapping(value = "/{eventId}", method = GET)
    public Event showEventDetails(final @PathVariable("eventId") UUID eventId) {
        return service.showDetails(eventId);
    }


    @RequestMapping(value = "/event", method = GET)
    public Event showEventByName(final @RequestParam(value = "name", required = true) String name) {
        return service.findByName(name);
    }

    @RequestMapping(value = "/{eventId}/subscribe", method = POST)
    public void subscribe(@PathVariable(value = "eventId") UUID eventId, Principal principal) {
        service.subscribe(eventId, principal);
    }
    @RequestMapping(value = "/{eventId}/unsubscribe", method = POST)
    public void unsubscribe(@PathVariable(value = "eventId") UUID eventId, Principal principal) {
        service.unsubscribe(eventId, principal);
    }
}