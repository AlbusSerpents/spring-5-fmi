package com.ddd.books.in.spring.func.events;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class EventsService {
    private final EventsRepository repository;

    public EventsService(final EventsRepository repository) {
        this.repository = repository;
    }

    public Event create(final Event event) {
        return repository.add(event);
    }

    public List<Event> listAll(final String topic) {
        return repository.findAll(topic);
    }

    public Event showDetails(final UUID id) {
        return repository.findById(id);
    }

    public Event findByName(String name) {
        return repository.findByName(name);
    }

    public void subscribe(UUID eventId, Principal principal) {
        repository.subscribe(eventId, principal);
    }

    public void unsubscribe(UUID eventId, Principal principal) {
        repository.unsubscribe(eventId, principal);
    }

    public void delete(UUID id) {
        repository.delete(id);
    }


}
