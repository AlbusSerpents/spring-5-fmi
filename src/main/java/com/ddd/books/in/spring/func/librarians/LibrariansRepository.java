package com.ddd.books.in.spring.func.librarians;

import java.util.Optional;

public interface LibrariansRepository {

    Optional<Librarian> findByUsername(final String username);
}
