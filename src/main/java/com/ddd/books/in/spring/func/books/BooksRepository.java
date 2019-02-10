package com.ddd.books.in.spring.func.books;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BooksRepository {

    List<BookInfo> findNewest();

    Book save(final Book book);

    Optional<Book> findById(final UUID bookId);

    List<BookInfo> findAll(final BooksSearch search);

    void pushRating(UUID bookId, BookRatingRequest rating);
}
