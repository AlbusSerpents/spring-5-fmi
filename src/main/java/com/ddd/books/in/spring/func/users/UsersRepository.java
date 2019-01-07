package com.ddd.books.in.spring.func.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.UUID;
import java.util.stream.Stream;

public interface UsersRepository extends MongoRepository<User, UUID> {

    @Query("{$or: [{name: '?0'}, {email: '?1'}]}")
    Stream<User> findByExisting(final String name, final String email);
}
