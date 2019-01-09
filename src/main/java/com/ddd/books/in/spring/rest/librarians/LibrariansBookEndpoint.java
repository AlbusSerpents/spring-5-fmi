package com.ddd.books.in.spring.rest.librarians;

import com.ddd.books.in.spring.func.books.Book;
import com.ddd.books.in.spring.func.books.BooksService;
import com.ddd.books.in.spring.func.books.CreateBookRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/librarians/books")
public class LibrariansBookEndpoint {

    private final BooksService service;

    public LibrariansBookEndpoint(final BooksService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "", method = POST)
    public Book create(final @RequestBody @Valid CreateBookRequest request) {
        return service.create(request);
    }
}
