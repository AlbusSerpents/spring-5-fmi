package com.ddd.books.in.spring.func.books.wishlists;

import java.util.List;
import java.util.UUID;

public interface WishlistsRepository {
    List<BookWish> findWishlist(UUID userId);
}
