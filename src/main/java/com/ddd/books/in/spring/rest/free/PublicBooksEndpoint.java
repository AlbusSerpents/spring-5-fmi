package com.ddd.books.in.spring.rest.free;

import com.ddd.books.in.spring.func.books.BookDetails;
import com.ddd.books.in.spring.func.books.BookInfo;
import com.ddd.books.in.spring.func.books.BooksSearch;
import com.ddd.books.in.spring.func.books.BooksService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/v1/public/books")
public class PublicBooksEndpoint {

    private final BooksService service;

    public PublicBooksEndpoint(final BooksService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = GET)
    public List<BookInfo> listBooks(final @Valid BooksSearch search) {
        return service.findAll(search);
    }

    @RequestMapping(value = "/{bookId}", method = GET)
    public BookDetails getById(final @PathVariable("bookId") UUID bookId) {
        return service.findById(bookId);
    }
}
