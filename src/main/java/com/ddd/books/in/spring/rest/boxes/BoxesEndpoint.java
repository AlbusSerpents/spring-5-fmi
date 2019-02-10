package com.ddd.books.in.spring.rest.boxes;

import com.ddd.books.in.spring.func.boxes.BookInBox;
import com.ddd.books.in.spring.func.boxes.BookInBoxService;
import com.ddd.books.in.spring.func.boxes.Box;
import com.ddd.books.in.spring.func.boxes.BoxesService;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/boxes")
public class BoxesEndpoint {
    private final BoxesService service;
    private final BookInBoxService bookInBoxService;

    public BoxesEndpoint(BoxesService service, BookInBoxService bookInBoxService) {
        this.service = service;
        this.bookInBoxService = bookInBoxService;
    }

    @RequestMapping(value = "", method = GET)
    public List<Box> listBooks() {
        return service.findAll();
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/add", method = POST)
    public Box createBox(final @RequestBody Box box) {
        return service.create(box);
    }

    @RequestMapping(value = "/books", method = GET)
    public List<BookInBox> getAllBooks() {
        return this.bookInBoxService.getAll();
    }

    @RequestMapping(value = "/{boxId}/books", method = GET)
    public List<BookInBox> getAllBooksByBoxId(final @PathVariable("boxId") UUID boxId) {
        return this.bookInBoxService.getAllByBoxId(boxId);
    }

    @RequestMapping(value = "/add/book", method = POST)
    public BookInBox addBook(@RequestBody final BookInBox book)throws FunctionalException {
        return this.bookInBoxService.add(book);
    }

    @RequestMapping(value = "/book/{id}", method = DELETE)
    public void removeBookById(@PathVariable("id") UUID id) {
        this.bookInBoxService.removeBookById(id);
    }

    @RequestMapping(value = "/book/{id}", method = GET)
    public BookInBox getBookById(@PathVariable("id") UUID id) {
        return this.bookInBoxService.getBookById(id);
    }


}
