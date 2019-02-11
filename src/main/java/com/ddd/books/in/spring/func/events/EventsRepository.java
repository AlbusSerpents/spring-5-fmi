package com.ddd.books.in.spring.func.events;


import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface EventsRepository {
    Event add(final Event event);

    List<Event> findAll(final String topic);

    Event findByName(final String name);

    Event findById(final UUID id);

    void delete(final UUID id);

    List<Event> findAllByName(final String name);

    void subscribe(UUID eventId, Principal principal);

    void unsubscribe(UUID eventId, Principal principal);
}
