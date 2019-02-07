package com.ddd.books.in.spring.func.books;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class BookInfo {
    private UUID id;
    private String name;
    private String author;
    private Integer publishingYear;
    private String bookCover;
}
