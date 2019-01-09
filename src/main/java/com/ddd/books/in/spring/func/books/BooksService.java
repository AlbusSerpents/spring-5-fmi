package com.ddd.books.in.spring.func.books;

import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;

@Service
public class BooksService {

    private final BooksRepository repository;

    public BooksService(final BooksRepository repository) {
        this.repository = repository;
    }

    public Book create(final CreateBookRequest request) {
        final Book book = new Book(
                randomUUID(),
                request.getName(),
                request.getAuthor(),
                request.getPublishingYear(),
                request.getDescription(),
                request.getContents(),
                emptyList(),
                emptyList());

        return repository.save(book);
    }
}
