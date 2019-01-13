package com.ddd.books.in.spring.func.boxes;

import com.ddd.books.in.spring.func.exceptions.FunctionalException;

import java.util.List;
import java.util.UUID;

public interface BookInBoxRepository {
    BookInBox add(BookInBox box) throws FunctionalException;

    List<BookInBox> getAll();

    List<BookInBox> getAllByBoxId(UUID id);

    void removeBookById(String id);
}
