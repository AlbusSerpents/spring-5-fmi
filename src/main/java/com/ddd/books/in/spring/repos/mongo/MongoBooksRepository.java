package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.books.Book;
import com.ddd.books.in.spring.func.books.BooksRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

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
}
