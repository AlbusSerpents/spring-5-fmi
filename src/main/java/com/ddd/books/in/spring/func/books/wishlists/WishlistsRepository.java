package com.ddd.books.in.spring.func.books.wishlists;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface WishlistsRepository {
    Optional<Set<BookWish>> findWishlist(UUID userId);

    boolean updateWishlist(Set<BookWish> wishlist, UUID ownerId);
}
