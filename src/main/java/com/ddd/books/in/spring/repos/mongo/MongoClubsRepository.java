package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.clubs.Club;
import com.ddd.books.in.spring.func.clubs.ClubInfo;
import com.ddd.books.in.spring.func.clubs.ClubsRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MongoClubsRepository implements ClubsRepository {

    private final MongoTemplate template;

    public MongoClubsRepository(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public Club save(final Club club) {
        template.save(club);
        return template.findById(club.getId(), Club.class);
    }

    @Override
    public Optional<Club> findByName(final String name) {
        final Query query = new Query(where("name").is(name));
        final Club club = template.findOne(query, Club.class);
        return ofNullable(club);
    }

    @Override
    public List<ClubInfo> findAllInfo() {
        return template.findAll(ClubInfo.class, "club");
    }
}
