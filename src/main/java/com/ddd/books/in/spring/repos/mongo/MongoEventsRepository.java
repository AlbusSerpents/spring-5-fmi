package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.events.Event;
import com.ddd.books.in.spring.func.events.EventsRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class MongoEventsRepository implements EventsRepository {
    private final MongoTemplate template;

    public MongoEventsRepository(final MongoTemplate template) {
        this.template = template;
    }


    @Override
    public Event add(final Event event) {
        event.setParticipants(new ArrayList<>());
        template.save(event);
        return event;
    }

    @Override
    public List<Event> findAll() {
        return template.findAll(Event.class);
    }

    @Override
    public Event findById(UUID id) {
        final Event event = template.findById(id, Event.class);
        return event;
    }

    @Override
    public void delete(UUID id) {
        template.remove(Query.query(Criteria.where("id").is(id)),Event.class);
    }

    @Override
    public List<Event> findAllByName(String name) {
        return null;
    }

    @Override
    public void subscribe(UUID eventId, Principal principal) {
        final Event event = findById(eventId);
        if (event.getParticipants().indexOf(principal.getName()) == -1) {
            event.getParticipants().add(principal.getName());
            template.save(event);
        }
    }

    @Override
    public void unsubscribe(UUID eventId, Principal principal) {
        final Event event = findById(eventId);
        if (event.getParticipants().indexOf(principal.getName()) != -1) {
            int index = event.getParticipants().indexOf(principal.getName());
            event.getParticipants().remove(index);
            template.save(event);
        }
    }

    @Override
    public Event findByName(final String name) {
        return template.findOne(Query.query(Criteria.where("name").is(name)), Event.class);
    }
}
