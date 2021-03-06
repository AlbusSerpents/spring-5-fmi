package com.ddd.books.in.spring.repos.mongo;

import com.ddd.books.in.spring.func.users.User;
import com.ddd.books.in.spring.func.users.UserInfo;
import com.ddd.books.in.spring.func.users.UsersRepository;
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
public class MongoUsersRepository implements UsersRepository {

    private final MongoTemplate template;

    public MongoUsersRepository(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public Optional<User> findById(final UUID id) {
        final User user = template.findById(id, User.class);
        return ofNullable(user);
    }

    @Override
    public List<UserInfo> findByNameOrEmail(final String name, final String email) {
        if (name == null && email == null) {
            return template.findAll(UserInfo.class, "user");
        } else if (name != null && email == null) {
            return findAllByName(name);
        } else if (name == null) {
            return findAllByEmail(email);
        } else {
            return findByExisting(name, email);
        }
    }

    private List<UserInfo> findAllByEmail(final String email) {
        if (email == null) {
            return template.findAll(UserInfo.class);
        } else {
            final Query query = new Query(where("email").is(email));
            return template.find(query, UserInfo.class);
        }
    }

    private List<UserInfo> findAllByName(final String name) {
        if (name == null) {
            return template.findAll(UserInfo.class);
        } else {
            final Query query = new Query(where("name").is(name));
            return template.find(query, UserInfo.class);
        }
    }

    @Override
    public User save(final User user) {
        template.save(user);
        return template.findById(user.getId(), User.class);
    }

    @Override
    public List<UserInfo> findByExisting(final String name, final String email) {
        final Criteria orCriteria = new Criteria()
                .orOperator(where("name").is(name), where("email").is(email));
        final Query query = new Query(orCriteria);
        return template.find(query, UserInfo.class, "user");
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        final Query query = new Query(where("email").is(email));
        final User user = template.findOne(query, User.class);
        return ofNullable(user);
    }

    @Override
    public boolean removeById(final UUID userId) {
        final Query query = new Query(where("_id").is(userId));
        final DeleteResult result = template.remove(query, "user");
        return result.getDeletedCount() == 1;
    }
}
