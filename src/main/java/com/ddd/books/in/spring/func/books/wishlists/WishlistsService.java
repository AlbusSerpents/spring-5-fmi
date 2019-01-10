package com.ddd.books.in.spring.func.books.wishlists;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WishlistsService {

    private final WishlistsRepository repository;

    public WishlistsService(final WishlistsRepository repository) {
        this.repository = repository;
    }

    public List<BookWish> readWishlist(final UUID userId) {
        return repository.findWishlist(userId);
    }
}
