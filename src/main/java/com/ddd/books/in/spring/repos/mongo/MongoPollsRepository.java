package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.books.poll.BookInPoll;
import com.ddd.books.in.spring.func.books.poll.Poll;
import com.ddd.books.in.spring.func.books.poll.PollRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Repository
public class MongoPollsRepository implements PollRepository {

    private final MongoTemplate template;

    public MongoPollsRepository(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public Poll save(final Poll poll) {
        template.save(poll);
        return template.findById(poll.getId(), Poll.class);
    }

    @Override
    public Optional<Poll> findById(final UUID pollId) {
        final Poll poll = template.findById(pollId, Poll.class);
        return ofNullable(poll);
    }

    @Override
    public List<BookInPoll> findAll() {
        List<Poll> polls = template.findAll(Poll.class, "poll");
        int latestPollIndex = polls.size() - 1;
        Poll latestPoll = polls.get(latestPollIndex);

        return latestPoll.getBooksInPoll();
    }
}
