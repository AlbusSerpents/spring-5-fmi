package com.ddd.books.in.spring.func.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private List<Marker> contents;

    private List<Integer> ratings;
    private List<Comment> comments;

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
    public static class Comment {
        private UUID commenterId;
        private String comment;
    }
}
