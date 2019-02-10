package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.boxes.BookInBox;
import com.ddd.books.in.spring.func.boxes.BookInBoxRepository;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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
    public List<BookInBox> getAllByBoxId(UUID id) {
        return template.find(Query.query(Criteria.where("boxId").is(id.toString())), BookInBox.class);
    }

    @Override
    public void removeBookById(UUID id) {
        this.template.remove(Query.query(Criteria.where("id").is(id.toString())), BookInBox.class);
    }

    @Override
    public BookInBox getBookById(UUID id) {
        return this.template.findById(id.toString(), BookInBox.class);
    }

}
