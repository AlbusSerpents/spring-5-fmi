package com.ddd.books.in.spring.func.boxes;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BoxesService {
    private final BoxRepository repository;

    public BoxesService(final BoxRepository repository) {  this.repository = repository; }

    public Box create(final Box box) {
        return repository.save(box);
    }

    public List<Box> findAll() {
        return repository.findAll();
    }
}
