package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.books.Book;
import com.ddd.books.in.spring.func.books.BookInfo;
import com.ddd.books.in.spring.func.books.BooksRepository;
import com.ddd.books.in.spring.func.books.BooksSearch;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;

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

    @Override
    public List<BookInfo> findAll(final BooksSearch search) {
        final Criteria nameCriteria = search.getName() == null ? new Criteria() : where("name").is(search.getName());
        final Criteria authorCriteria = search.getAuthor() == null ? new Criteria() : where("author").is(search.getAuthor());
        final Criteria yearCriteria = search.getPublishingYear() == null ? new Criteria() : where("publishingYear").is(search.getPublishingYear());

        final Criteria criteria = new Criteria().andOperator(nameCriteria, authorCriteria, yearCriteria);

        return template.find(new Query(criteria), BookInfo.class, "book");
    }
}
