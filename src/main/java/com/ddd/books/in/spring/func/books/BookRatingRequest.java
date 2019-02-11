package com.ddd.books.in.spring.func.books;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class BookRatingRequest {
    @Min(0)
    @Max(100)
    @NotNull
    private Integer rating;
}
