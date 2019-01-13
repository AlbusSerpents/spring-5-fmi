package com.ddd.books.in.spring.func.boxes;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoxRepository {
     List<Box>  findAll();
     Optional<Box> findById(final UUID boxId);
     Box save(Box box);
}
