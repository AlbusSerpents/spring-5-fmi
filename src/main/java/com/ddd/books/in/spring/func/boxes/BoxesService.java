package com.ddd.books.in.spring.func.boxes;

import org.springframework.stereotype.Service;

@Service
public class BoxesService {
    private final BoxesRepository repository;

    public BoxesService(final BoxesRepository repository) {  this.repository = repository; }
}
