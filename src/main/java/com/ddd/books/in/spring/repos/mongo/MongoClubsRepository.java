package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.clubs.Club;
import com.ddd.books.in.spring.func.clubs.ClubInfo;
import com.ddd.books.in.spring.func.clubs.ClubsRepository;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<Club> findById(final UUID id) {
        final Club club = template.findById(id, Club.class);
        return ofNullable(club);
    }

    @Override
    public List<ClubInfo> findAllInfo(final String name, final String topic) {
        if (name == null && topic == null) {
            return template.findAll(ClubInfo.class, "club");
        } else if (name != null && topic == null) {
            return findAllByName(name);
        } else if (name == null) {
            return findAllByTopic(topic);
        } else {
            return findAllByExisting(name, topic);
        }
    }

    @Override
    public List<ClubInfo> findAllByName(final String name) {
        final Query query = new Query(where("name").is(name));
        return template.find(query, ClubInfo.class, "club");
    }

    @Override
    public List<ClubInfo> findAllByTopic(final String topic) {
        final Query query = new Query(where("topic").is(topic));
        return template.find(query, ClubInfo.class, "club");
    }

    @Override
    public List<ClubInfo> findAllByExisting(final String name, final String topic) {
        final Criteria andCriteria = new Criteria()
                .andOperator(where("name").is(name), where("topic").is(topic));
        final Query query = new Query(andCriteria);
        return template.find(query, ClubInfo.class, "club");
    }

    @Override
    public boolean deleteById(final UUID clubId) {
        final Query query = new Query(where("_id").is(clubId));
        final DeleteResult delete = template.remove(query, "club");
        return delete.getDeletedCount() == 1;
    }

    @Override
    public List<ClubInfo> findMyClubs(final UUID userId) {
        final Criteria criteria = where("members").elemMatch(where("_id").is(userId));
        return template.find(new Query(criteria), ClubInfo.class, "club");
    }
}
