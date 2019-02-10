package com.ddd.books.in.spring.rest.librarians;

import com.ddd.books.in.spring.func.books.poll.Poll;
import com.ddd.books.in.spring.func.books.poll.PollService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/librarians/poll")
public class LibrariansPollEndpoint {

    private final PollService service;

    public LibrariansPollEndpoint(final PollService service) {
        this.service = service;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "", method = POST)
    public Poll create() {
        return service.create();
    }
}
