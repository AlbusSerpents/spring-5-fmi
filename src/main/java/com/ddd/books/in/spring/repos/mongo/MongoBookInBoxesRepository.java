package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.books.Book;
import com.ddd.books.in.spring.func.boxes.BookInBox;
import com.ddd.books.in.spring.func.boxes.BookInBoxRepository;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoBookInBoxesRepository implements BookInBoxRepository {

    private final MongoTemplate template;

    public MongoBookInBoxesRepository(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public BookInBox add(BookInBox box) throws FunctionalException {
        template.save(box);
        return box;
    }

    @Override
    public List<BookInBox> getAll() {
        return this.template.findAll(BookInBox.class);
    }

    @Override
    public List<BookInBox> getAllByBoxId(String id) {
        return template.find(Query.query(Criteria.where("boxId").is(id)), BookInBox.class);
    }

    @Override
    public void removeBookById(String id) {
        this.template.remove(Query.query(Criteria.where("id").is(id)), BookInBox.class);
    }

    @Override
    public BookInBox getBookById(String id) {
        return this.template.findById(id, BookInBox.class);
    }

}
