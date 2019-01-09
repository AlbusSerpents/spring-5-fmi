package com.ddd.books.in.spring.func.books;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BooksSearch {
    private String name;
    private String author;
    private Integer publishingYear;
}
