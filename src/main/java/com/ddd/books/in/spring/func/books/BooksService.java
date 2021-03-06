package com.ddd.books.in.spring.func.books;

import com.ddd.books.in.spring.func.books.Book.Comment;
import com.ddd.books.in.spring.func.books.Book.Marker;
import com.ddd.books.in.spring.func.books.wishlists.BookWish;
import com.ddd.books.in.spring.func.books.wishlists.WishlistsRepository;
import com.ddd.books.in.spring.func.books.wishlists.WishlistsService;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import com.ddd.books.in.spring.func.users.User;
import com.ddd.books.in.spring.func.users.UserInfo;
import com.ddd.books.in.spring.func.users.UsersService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.BOOK_ALREADY_EXISTS;
import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.MISSING;
import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BooksService {

    private final BooksRepository repository;
    private final WishlistsRepository wishlistsRepository;
    private final UsersService usersService;

    public BooksService(final BooksRepository repository,
                        final WishlistsRepository wishlistsRepository,
                        final UsersService usersService) {
        this.repository = repository;
        this.wishlistsRepository = wishlistsRepository;
        this.usersService = usersService;
    }

    public Book create(final CreateBookRequest request) {

        if (bookAlreadyExists(request.getName())) {
            throw new FunctionalException(BOOK_ALREADY_EXISTS, "That book already exists", CONFLICT);
        } else {
            removeBookFromWishlists(request.getName(), request.getAuthor(), request.getPublishingYear());
            final Book book = new Book(
                    randomUUID(),
                    request.getName(),
                    request.getAuthor(),
                    request.getPublishingYear(),
                    request.getDescription(),
                    request.getBookCover(),
                    request.getContents(),
                    emptyList(),
                    emptyList());

            return repository.save(book);
        }
    }

    public List<BookInfo> readNewest() {
        return repository.findNewest();
    }

    public List<BookInfo> readAll(final BooksSearch search) {
        return repository.findAll(search);
    }

    public BookDetails readById(final UUID bookId) {
        final Book book = repository
                .findById(bookId)
                .orElseThrow(() -> bookNotFound(bookId));

        final List<Integer> ratings = book.getRatings();

        return new BookDetails(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getPublishingYear(),
                book.getDescription(),
                book.getBookCover(),
                ratings.isEmpty() ? null : calculateAverageRating(ratings),
                book.getComments());
    }

    private FunctionalException bookNotFound(final UUID bookId) {
        final String message = String.format("Book %s doesn't exist", bookId);
        return new FunctionalException(MISSING, message, NOT_FOUND);
    }

    public boolean bookAlreadyExists(final String bookName) {
        final BooksSearch search = new BooksSearch(bookName, null, null);
        return !repository.findAll(search).isEmpty();
    }

    private Integer calculateAverageRating(final List<Integer> ratings) {
        if (!ratings.isEmpty()) {
            final int ratingsSum = ratings
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum();
            return ratingsSum / ratings.size();
        } else {
            return null;
        }
    }

    public void rate(final UUID bookId, final BookRatingRequest rating) {
        repository.pushRating(bookId, rating);
    }

    public List<Comment> commentOnBook(
            final UUID bookId,
            final NewCommentRequest request) {
        final Book book = repository
                .findById(bookId)
                .orElseThrow(() -> bookNotFound(bookId));

        final Comment newComment = request.toComment();
        final Book newBook = book.addComment(newComment);

        return repository
                .save(newBook)
                .getComments();
    }

    public List<Marker> readContents(final UUID bookId) {
        return repository
                .findById(bookId)
                .map(Book::getContents)
                .orElseThrow(() -> bookNotFound(bookId));
    }

    private void removeBookFromWishlists(final String name, final String author, final Integer publishingYear) {
        List<UserInfo> users = usersService.findAll(null, null);

        for (UserInfo currentUser : users) {
            final Set<BookWish> wishlist = wishlistsRepository.findWishlist(currentUser.getId()).orElseThrow(() -> new FunctionalException(MISSING, null, NOT_FOUND));

            for (BookWish book: wishlist) {
                if(book.getName().equals(name) && book.getAuthor().equals(author) && book.getPublishingYear().equals(publishingYear)){
                    User user = usersService.readById(currentUser.getId());
                    user.getWishlist().remove(book);
                    usersService.updateWishlist(user);
                }
            }
        }
    }
}
