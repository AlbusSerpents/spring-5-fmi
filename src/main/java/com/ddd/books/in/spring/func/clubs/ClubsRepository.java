package com.ddd.books.in.spring.func.clubs;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClubsRepository extends MongoRepository<Club, UUID> {

    @Query("{name: '?0'}")
    Optional<Club> findByName(final String name);
}
