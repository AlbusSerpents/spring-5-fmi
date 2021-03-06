package com.ddd.books.in.spring.func.clubs;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubsRepository {

    Club save(final Club club);

    Optional<Club> findByName(final String name);

    Optional<Club> findById(final UUID id);

    List<ClubInfo> findAllInfo(final String name, final String topic);

    List<ClubInfo> findAllByName(final String name);

    List<ClubInfo> findAllByTopic(final String topic);

    List<ClubInfo> findAllByExisting(final String name, final String topic);

    boolean deleteById(UUID clubId);

    List<ClubInfo> findMyClubs(UUID userId);
}
