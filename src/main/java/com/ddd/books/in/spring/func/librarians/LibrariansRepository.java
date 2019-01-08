package com.ddd.books.in.spring.func.librarians;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface LibrariansRepository extends MongoRepository<Librarian, UUID> {

    @Query("{username: '?0'}")
    Optional<Librarian> findByUsername(final String username);
}
