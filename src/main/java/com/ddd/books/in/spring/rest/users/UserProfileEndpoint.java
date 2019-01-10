package com.ddd.books.in.spring.rest.users;

import com.ddd.books.in.spring.auth.CustomUserDetails;
import com.ddd.books.in.spring.func.books.wishlists.BookWish;
import com.ddd.books.in.spring.func.books.wishlists.WishlistsService;
import com.ddd.books.in.spring.func.clubs.ClubInfo;
import com.ddd.books.in.spring.func.clubs.ClubsService;
import com.ddd.books.in.spring.func.users.UpdateUserRequest;
import com.ddd.books.in.spring.func.users.User;
import com.ddd.books.in.spring.func.users.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/v1/users")
public class UserProfileEndpoint {

    private final UsersService service;
    private final ClubsService clubsService;
    private final WishlistsService wishlistsService;

    public UserProfileEndpoint(final UsersService service, final ClubsService clubsService, final WishlistsService wishlistsService) {
        this.service = service;
        this.clubsService = clubsService;
        this.wishlistsService = wishlistsService;
    }

    @RequestMapping(value = "/profile/{userId}", method = GET)
    public Object getProfile(final @AuthenticationPrincipal CustomUserDetails details) {
        return service.readById(details.getId());
    }

    @RequestMapping(value = "/profile/{userId}", method = PUT)
    public User updateProfile(
            final @RequestBody @Valid UpdateUserRequest request,
            final @AuthenticationPrincipal CustomUserDetails details) {
        return service.update(details.getId(), request);
    }

    @RequestMapping(value = "/profile/{userId}/wishlist", method = GET)
    public List<BookWish> getWishlist(final @PathVariable("userId") UUID userId) {
        return wishlistsService.readWishlist(userId);
    }

    @RequestMapping(value = "/{userId}/my-clubs", method = GET)
    public List<ClubInfo> getMyClubs(final @AuthenticationPrincipal CustomUserDetails details) {
        return clubsService.readMyClubs(details.getId());
    }
}
