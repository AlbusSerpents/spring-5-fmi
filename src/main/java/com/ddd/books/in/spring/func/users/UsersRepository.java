package com.ddd.books.in.spring.func.users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersRepository {

    Optional<User> findById(final UUID id);

    List<UserInfo> findByNameOrEmail(final String name, final String email);

    User save(final User user);

    List<UserInfo> findByExisting(final String name, final String email);

    Optional<User> findByEmail(final String email);

    boolean removeById(final UUID userId);
}
