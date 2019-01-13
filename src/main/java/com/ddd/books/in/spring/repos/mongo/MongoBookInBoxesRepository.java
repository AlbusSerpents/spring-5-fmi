package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.boxes.BookInBox;
import com.ddd.books.in.spring.func.boxes.BookInBoxRepository;
import com.ddd.books.in.spring.func.boxes.Box;
import com.ddd.books.in.spring.func.exceptions.ErrorResponse;
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
    public BookInBox add(BookInBox book) throws FunctionalException {
        if (template.exists(Query.query(Criteria.where("id").is(book.getBoxId())), Box.class)) {
            template.save(book);
            return template.findById(book.getId(), BookInBox.class);
        }
        throw new FunctionalException(ErrorResponse.ErrorCode.BOX_NOT_FOUND,"Could not find box with such id.");
    }

    @Override
    public List<BookInBox> getAll() {
        return this.template.findAll(BookInBox.class);
    }

    @Override
    public List<BookInBox> getAllByBoxId(UUID id) {
        return template.find(Query.query(Criteria.where("boxId").is(id)), BookInBox.class);
    }

    @Override
    public void removeBookById(String id) {
        this.template.remove(Query.query(Criteria.where("id").is(id)), BookInBox.class);
    }
}
