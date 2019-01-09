package com.ddd.books.in.spring.func.books;

import com.ddd.books.in.spring.func.books.Book.Rating;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.MISSING;
import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class BooksService {

    private final BooksRepository repository;

    public BooksService(final BooksRepository repository) {
        this.repository = repository;
    }

    public Book create(final CreateBookRequest request) {
        final Book book = new Book(
                randomUUID(),
                request.getName(),
                request.getAuthor(),
                request.getPublishingYear(),
                request.getDescription(),
                request.getContents(),
                emptyList(),
                emptyList());

        return repository.save(book);
    }

    public BookDetails findById(final UUID bookId) {
        final Book book = repository
                .findById(bookId)
                .orElseThrow(() -> bookNotFound(bookId));

        final List<Rating> ratings = book.getRatings();

        return new BookDetails(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getPublishingYear(),
                book.getDescription(),
                ratings.isEmpty() ? null : calculateAverageRating(ratings),
                book.getComments());
    }

    private FunctionalException bookNotFound(final UUID bookId) {
        final String message = String.format("Book %s doesn't exist", bookId);
        return new FunctionalException(MISSING, message, NOT_FOUND);
    }

    private Integer calculateAverageRating(final List<Rating> ratings) {
        if (!ratings.isEmpty()) {
            final int ratingsSum = ratings
                    .stream()
                    .map(Rating::getRating)
                    .mapToInt(Integer::intValue)
                    .sum();

            return ratingsSum / ratings.size();
        } else {
            return null;
        }
    }
}
