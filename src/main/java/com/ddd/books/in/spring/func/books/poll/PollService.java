package com.ddd.books.in.spring.func.books.poll;

import com.ddd.books.in.spring.func.books.wishlists.BookWish;
import com.ddd.books.in.spring.func.books.wishlists.WishlistsService;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import com.ddd.books.in.spring.func.users.User;
import com.ddd.books.in.spring.func.users.UserInfo;
import com.ddd.books.in.spring.func.users.UsersService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.BOOK_NOT_IN_POLL;
import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.NO_POLLS_CREATED;
import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.USER_ALREADY_VOTED;
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

        Poll poll = new Poll(randomUUID(), booksInPoll, endDate, new ArrayList<>());
        final Poll createdPoll = repository.save(poll);

        return createdPoll;
    }

    public PollResults findResults() {
        Poll poll = repository.findLatestPoll();

        if (poll == null) {
            throw new FunctionalException(
                    NO_POLLS_CREATED,
                    "There are no polls created yet");
        }
        else {
            List<BookInPoll> books = readAll();
            Collections.sort(books, (first, second) -> {
                if (first.getVotes() > second.getVotes()) {
                    return -1;
                } else if (first.getVotes() < second.getVotes()) {
                    return 1;
                } else {
                    return 0;
                }
            });

            List<BookWish> winners = new ArrayList<>();
            List<BookWish> losers = new ArrayList<>();
            for (BookInPoll book : books) {
                int index = books.indexOf(book);
                if (index < 5) {
                    winners.add(book.getBook());
                }else{
                    losers.add(book.getBook());
                }
            }

            PollResults results = new PollResults(winners, losers);

            return results;
        }
    }

    public List<BookInPoll> readAll() {
        List<BookInPoll> books = repository.findAll();
        if(books != null){
            return books;
        }else{
            throw new FunctionalException(
                    NO_POLLS_CREATED,
                    "There are no polls created yet");
        }
    }

    public Poll vote(final UUID bookId, final UUID userId) {
        Poll poll = repository.findLatestPoll();

        if (poll == null) {
            throw new FunctionalException(
                    NO_POLLS_CREATED,
                    "There are no polls created yet");
        }
        else {
            List<UUID> voters = poll.getVoters();

            List<BookInPoll> books = repository.findAll();
            boolean bookInPoll = false;
            int bookIndex = -1;

            for (BookInPoll book : books) {
                if (book.getId().equals(bookId)) {
                    bookInPoll = true;
                    bookIndex = books.indexOf(book);
                    break;
                }
            }

            if (voters.contains(userId)) {
                throw new FunctionalException(
                        USER_ALREADY_VOTED,
                        "User has already cast their vote in this poll");
            } else if (!bookInPoll) {
                throw new FunctionalException(
                        BOOK_NOT_IN_POLL,
                        "This book is not in the current poll");
            } else {
                poll.getVoters().add(userId);
                Integer newVotes = poll.getBooksInPoll().get(bookIndex).getVotes() + 1;
                poll.getBooksInPoll().get(bookIndex).setVotes(newVotes);
                repository.save(poll);
            }

            return repository.findLatestPoll();
        }
    }

    private List<BookInPoll> getBooksFromWishlists() {
        List<UserInfo> users = usersService.findAll(null, null);
        List<BookInPoll> booksFromWishlists = new ArrayList<>();
        List<BookWish> books = new ArrayList<>();

        for (UserInfo user : users) {
            final Set<BookWish> wishlist = wishlistsService.readWishlist(user.getId());
            books.addAll(wishlist);
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
            BookWish book = getBookByName(entry.getKey(), books);
            if(book != null) {
                BookInPoll bookInPoll = new BookInPoll(randomUUID(), book, entry.getValue(), 0);
                booksFromWishlists.add(bookInPoll);
            }
        }

        return booksFromWishlists;
    }

    private BookWish getBookByName(final String name, final List<BookWish> books){
        for (BookWish book : books) {
            if (book.getName().equals(name)) {
                return book;
            }
        }
        return null;
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
