package com.ddd.books.in.spring.func.books.poll;

import java.util.Optional;
import java.util.UUID;

public interface PollRepository {

    Poll save(final Poll poll);

    Optional<Poll> findById(final UUID pollId);
}
