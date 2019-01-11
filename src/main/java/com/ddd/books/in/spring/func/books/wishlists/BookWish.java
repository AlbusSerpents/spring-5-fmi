package com.ddd.books.in.spring.func.books.wishlists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWish {
    @NotBlank
    private String name;
    @NotBlank
    private String author;

    @NotNull
    @Positive
    private Integer publishingYear;
}
