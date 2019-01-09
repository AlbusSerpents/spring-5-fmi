package com.ddd.books.in.spring.rest.users;

import com.ddd.books.in.spring.func.books.BookRatingRequest;
import com.ddd.books.in.spring.func.books.BooksService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("v1/users/books/")
public class UsersBooksEndpoint {

    private final BooksService service;

    public UsersBooksEndpoint(final BooksService service) {
        this.service = service;
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/{bookId}/rate", method = POST)
    public void rate(
            final @PathVariable("bookId") UUID bookId,
            final @RequestBody @Valid BookRatingRequest rating) {
        service.rate(bookId, rating);
    }
}
