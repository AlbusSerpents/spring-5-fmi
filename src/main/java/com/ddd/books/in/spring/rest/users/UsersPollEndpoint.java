package com.ddd.books.in.spring.rest.users;

import com.ddd.books.in.spring.auth.CustomUserDetails;
import com.ddd.books.in.spring.func.books.poll.Poll;
import com.ddd.books.in.spring.func.books.poll.PollService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/users/poll")
public class UsersPollEndpoint {

    private final PollService service;

    public UsersPollEndpoint(final PollService service) { this.service = service; }

    @RequestMapping(value = "/{bookId}", method = POST)
    public Poll vote(final @PathVariable("bookId") UUID bookId,
                     final @AuthenticationPrincipal CustomUserDetails details){
        return service.vote(bookId, details.getId());
    }

}
