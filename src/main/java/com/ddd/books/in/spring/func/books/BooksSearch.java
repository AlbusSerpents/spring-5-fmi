package com.ddd.books.in.spring.func.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksSearch {
    private String name;
    private String author;
    private Integer publishingYear;
}
