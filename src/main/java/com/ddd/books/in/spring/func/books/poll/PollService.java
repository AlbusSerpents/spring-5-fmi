package com.ddd.books.in.spring.func.books.poll;

import com.ddd.books.in.spring.func.books.wishlists.BookWish;
import com.ddd.books.in.spring.func.books.wishlists.WishlistsService;
import com.ddd.books.in.spring.func.users.User;
import com.ddd.books.in.spring.func.users.UsersService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static java.util.UUID.randomUUID;

@Service
public class PollService {

    private final PollRepository repository;
    private final WishlistsService wishlistsService;
    private final UsersService usersService;

    public PollService(final PollRepository repository, final WishlistsService wishlistsService, final UsersService usersService) {
        this.repository = repository;
        this.wishlistsService = wishlistsService;
        this.usersService = usersService;
    }

    public Poll create() {
        List<BookInPoll> booksFromWishlists = getBooksFromWishlists();
        List<BookInPoll> booksInPoll = addBooksInPoll(booksFromWishlists);

        LocalDate futureDate = LocalDate.now().plusMonths(1);
        Date endDate = Date.valueOf(futureDate);
        Poll poll = new Poll(randomUUID(), booksInPoll, endDate);
        final Poll createdPoll = repository.save(poll);

        return createdPoll;
    }

    public List<BookInPoll> readAll() {
        return repository.findAll();
    }

    private List<BookInPoll> getBooksFromWishlists() {
        List<User> users = usersService.findAll(null, null);
        List<BookInPoll> booksFromWishlists = new ArrayList<>();
        List<BookWish> books = new ArrayList<>();

        for (User user : users) {
            final Set<BookWish> wishlist = wishlistsService.readWishlist(user.getId());
            for (BookWish wish : wishlist) {
                books.add(wish);
            }
        }

        Map<String, Integer> bookOccurrences = new HashMap<>();
        for (BookWish book : books) {
            if (bookOccurrences.containsKey(book.getName())) {
                int newOccurrence = bookOccurrences.get(book.getName()) + 1;
                bookOccurrences.put(book.getName(), newOccurrence);
            } else {
                bookOccurrences.put(book.getName(), 1);
            }
        }

        for (Map.Entry<String, Integer> entry : bookOccurrences.entrySet()) {
            for (BookWish book : books) {
                if (book.getName().equals(entry.getKey())) {
                    BookInPoll bookInPoll = new BookInPoll(book, entry.getValue(), 0);
                    booksFromWishlists.add(bookInPoll);
                }
            }
        }

        return booksFromWishlists;
    }

    private List<BookInPoll> addBooksInPoll(final List<BookInPoll> booksFromWishlists) {

        Collections.sort(booksFromWishlists, (first, second) -> {
            if (first.getOccurrenceInWishlists() > second.getOccurrenceInWishlists()) {
                return -1;
            } else if (first.getOccurrenceInWishlists() < second.getOccurrenceInWishlists()) {
                return 1;
            } else {
                return 0;
            }
        });

        List<BookInPoll> booksInPoll = new ArrayList<>();
        for (BookInPoll book : booksFromWishlists) {
            if (booksInPoll.size() < 10) {
                booksInPoll.add(book);
            }
        }

        return booksInPoll;
    }


}
