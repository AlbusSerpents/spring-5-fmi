package com.ddd.books.in.spring.func.boxes;

import com.ddd.books.in.spring.func.exceptions.FunctionalException;

import java.util.List;


public interface BookInBoxRepository {
    BookInBox add(BookInBox box) throws FunctionalException;

    List<BookInBox> getAll();

    List<BookInBox> getAllByBoxId(String id);

    void removeBookById(String id);

    BookInBox getBookById(String id);
}
