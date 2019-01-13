package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.boxes.BookInBox;
import com.ddd.books.in.spring.func.boxes.Box;
import com.ddd.books.in.spring.func.boxes.BoxesRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class MongoBoxesRepository implements BoxesRepository {
    @Override
    public Box addBookToBox(BookInBox book) {
        return null;
    }

    @Override
    public List<Box> findAll() {
        return null;
    }

    @Override
    public boolean deleteBookfromBox(UUID bookId) {
        return false;
    }
}
