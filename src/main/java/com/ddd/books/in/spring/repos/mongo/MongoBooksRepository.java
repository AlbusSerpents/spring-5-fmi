package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.books.Book;
import com.ddd.books.in.spring.func.books.BooksRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Repository
public class MongoBooksRepository implements BooksRepository {

    private final MongoTemplate template;

    public MongoBooksRepository(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public Book save(final Book book) {
        template.save(book);
        return template.findById(book.getId(), Book.class);
    }

    @Override
    public Optional<Book> findById(final UUID bookId) {
        final Book book = template.findById(bookId, Book.class);
        return ofNullable(book);
    }
}
