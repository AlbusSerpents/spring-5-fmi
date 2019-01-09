package com.ddd.books.in.spring.func.books;

import java.util.Optional;
import java.util.UUID;

public interface BooksRepository {

    Book save(final Book book);

    Optional<Book> findById(final UUID bookId);
}
