package com.ddd.books.in.spring.func.books;

import com.ddd.books.in.spring.func.books.Book.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BookDetails {
    private UUID id;

    private String name;
    private String author;
    private Integer publishingYear;

    private String description;
    private String bookCover;

    private Integer rating;
    private List<Comment> comments;
}
