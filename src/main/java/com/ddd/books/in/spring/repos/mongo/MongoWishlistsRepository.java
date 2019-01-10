package com.ddd.books.in.spring.repos.mongo;


import com.ddd.books.in.spring.func.books.wishlists.BookWish;
import com.ddd.books.in.spring.func.books.wishlists.WishlistsRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MongoWishlistsRepository implements WishlistsRepository {

    private final MongoTemplate template;

    public MongoWishlistsRepository(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public List<BookWish> findWishlist(final UUID userId) {
        final Query query = new Query(where("_id").is(userId));
        query.fields().include("wishlist");
        return template.find(query, BookWish.class, "user");
    }
}
