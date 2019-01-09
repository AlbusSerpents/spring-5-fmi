package com.ddd.books.in.spring.func.clubs;

import java.util.Optional;

public interface ClubsRepository {

    Club save(final Club club);

    Optional<Club> findByName(final String name);
}
