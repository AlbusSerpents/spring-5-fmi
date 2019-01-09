package com.ddd.books.in.spring.func.users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersRepository {

    Optional<User> findById(final UUID id);

    User save(final User user);

    List<User> findByExisting(final String name, final String email);

    Optional<User> findByEmail(final String email);

    List<User> findByName(final String name);
}
