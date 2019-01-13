package com.ddd.books.in.spring.func.boxes;

import java.util.List;
import java.util.UUID;

public interface BoxesRepository  {
    public Box addBookToBox(BookInBox book);
    public List<Box>  findAll();
    public boolean deleteBookfromBox(final UUID bookId);
}
