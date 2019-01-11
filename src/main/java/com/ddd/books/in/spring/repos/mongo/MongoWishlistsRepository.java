package com.ddd.books.in.spring.repos.mongo;


import com.ddd.books.in.spring.func.books.wishlists.BookWish;
import com.ddd.books.in.spring.func.books.wishlists.WishlistsRepository;
import com.ddd.books.in.spring.func.users.User;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MongoWishlistsRepository implements WishlistsRepository {

    private final MongoTemplate template;

    public MongoWishlistsRepository(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public Optional<Set<BookWish>> findWishlist(final UUID userId) {
        return ofNullable(template.findById(userId, User.class))
                .map(User::getWishlist);
    }

    @Override
    public boolean updateWishlist(final Set<BookWish> wishlist, final UUID ownerId) {
        final Query query = new Query(where("_id").is(ownerId));
        final Update update = Update.update("wishlist", wishlist);
        final UpdateResult result = template.updateFirst(query, update, "user");
        return result.getModifiedCount() == 1;
    }
}
