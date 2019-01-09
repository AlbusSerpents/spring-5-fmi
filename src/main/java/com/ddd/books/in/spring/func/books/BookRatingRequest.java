package com.ddd.books.in.spring.func.books;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class BookRatingRequest {
    @Min(0)
    @Max(100)
    private Integer rating;
}
