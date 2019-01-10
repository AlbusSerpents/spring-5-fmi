package com.ddd.books.in.spring.func.books.wishlists;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookWish {
    private String name;
    private String author;
    private Integer publishingYear;
}
