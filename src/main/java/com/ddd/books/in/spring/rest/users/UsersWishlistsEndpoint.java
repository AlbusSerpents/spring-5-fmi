package com.ddd.books.in.spring.rest.users;

import com.ddd.books.in.spring.auth.CustomUserDetails;
import com.ddd.books.in.spring.func.books.wishlists.BookWish;
import com.ddd.books.in.spring.func.books.wishlists.WishlistsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/users/profile/{userId}/wishlist")
public class UsersWishlistsEndpoint {

    private final WishlistsService wishlistsService;

    public UsersWishlistsEndpoint(final WishlistsService wishlistsService) {
        this.wishlistsService = wishlistsService;
    }

    @RequestMapping(value = "", method = GET)
    public Set<BookWish> getWishlist(final @PathVariable("userId") UUID userId) {
        return wishlistsService.readWishlist(userId);
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/add", method = POST)
    public Set<BookWish> addToWishlist(
            final @PathVariable("userId") UUID ownerId,
            final @RequestBody @Valid BookWish request,
            final @AuthenticationPrincipal CustomUserDetails details) {
        return wishlistsService.addToWishlist(ownerId, details.getId(), request);
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/remove", method = POST)
    public void removeFromWishlist(
            final @PathVariable("userId") UUID ownerId,
            final @RequestBody @Valid BookWish request,
            final @AuthenticationPrincipal CustomUserDetails details){
        wishlistsService.removeFromWishlist(ownerId, details.getId(), request);
    }
}
