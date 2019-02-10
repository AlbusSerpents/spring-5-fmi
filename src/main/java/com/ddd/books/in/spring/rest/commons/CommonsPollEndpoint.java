package com.ddd.books.in.spring.rest.commons;

import com.ddd.books.in.spring.func.books.poll.BookInPoll;
import com.ddd.books.in.spring.func.books.poll.PollService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/v1/common/poll")
public class CommonsPollEndpoint {

    private final PollService service;

    public CommonsPollEndpoint(final PollService service) {
        this.service = service;
    }

    @RequestMapping(value = "/books", method = GET)
    public List<BookInPoll> getAll() {
        return service.readAll();
    }
}
