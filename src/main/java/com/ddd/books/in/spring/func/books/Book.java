package com.ddd.books.in.spring.func.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("WeakerAccess")
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private UUID id;

    private String name;
    private String author;
    private Integer publishingYear;

    private String description;
    private String bookCover;
    private List<Marker> contents;

    private List<Integer> ratings;
    private List<Comment> comments;

    public Book addComment(final Comment comment) {
        final Book newBook = new Book(
                id
                , name
                , author
                , publishingYear
                , description
                , bookCover
                , contents
                , ratings
                , new ArrayList<>(comments));

        newBook.comments.add(comment);
        return newBook;
    }

    @Data
    @Document
    @NoArgsConstructor
    public static class Marker {
        private String name;
        private Integer page;
    }

    @Data
    @Document
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Comment {
        private UUID commenterId;
        private String commenter;
        private String comment;
    }
}
