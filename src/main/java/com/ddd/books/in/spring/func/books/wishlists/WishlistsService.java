package com.ddd.books.in.spring.func.books.wishlists;

import com.ddd.books.in.spring.func.books.BooksService;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@Service
public class WishlistsService {

    private final WishlistsRepository repository;
    private final BooksService booksService;

    public WishlistsService(final WishlistsRepository repository, final BooksService booksService) {
        this.repository = repository;
        this.booksService = booksService;
    }

    public Set<BookWish> readWishlist(final UUID userId) {
        return repository.findWishlist(userId).orElseThrow(() -> new FunctionalException(MISSING, null, NOT_FOUND));
    }

    public Set<BookWish> addToWishlist(
            final UUID ownerId,
            final UUID userId,
            final BookWish request) {

        if (!ownerId.equals(userId)) {
            throw new FunctionalException(OPERATION_FORBIDDEN, null, FORBIDDEN);
        } else if (booksService.bookAlreadyExists(request.getName())) {
            throw new FunctionalException(BOOK_ALREADY_EXISTS, "That book already exists", CONFLICT);
        } else {
            final Set<BookWish> wishlist = readWishlist(ownerId);
            wishlist.add(request);
            final boolean success = repository.updateWishlist(wishlist, ownerId);

            if (success) {
                return readWishlist(ownerId);
            } else {
                throw new FunctionalException(OPERATION_FAILED, null);
            }
        }
    }
}
