package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.boxes.BookInBox;
import com.ddd.books.in.spring.func.boxes.Box;
import com.ddd.books.in.spring.func.boxes.BoxRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MongoBoxesRepository implements BoxRepository {
    private final MongoTemplate template;

    public MongoBoxesRepository(final MongoTemplate template) {
        this.template = template;
    }



    @Override
    public List<Box> findAll() {
        return template.findAll(Box.class);
    }

    @Override
    public Optional<Box> findById(final UUID boxId) {
        final Box box = template.findById(boxId, Box.class);
        return Optional.ofNullable(box);
    }

    @Override
    public Box save(final Box box){
        template.save(box);
        return template.findById(box.getId(), Box.class);
    }
}
