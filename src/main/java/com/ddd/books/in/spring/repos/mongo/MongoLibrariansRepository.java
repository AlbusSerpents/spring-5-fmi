package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.librarians.Librarian;
import com.ddd.books.in.spring.func.librarians.LibrariansRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MongoLibrariansRepository implements LibrariansRepository {
    private final MongoTemplate template;

    public MongoLibrariansRepository(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public Optional<Librarian> findByUsername(final String username) {
        final Query query = new Query(where("username").is(username));
        return ofNullable(template.findOne(query, Librarian.class));
    }
}
