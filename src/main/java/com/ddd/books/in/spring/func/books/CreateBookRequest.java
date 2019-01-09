package com.ddd.books.in.spring.func.books;

import com.ddd.books.in.spring.func.books.Book.Marker;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateBookRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String author;

    @NotNull
    @Positive
    private Integer publishingYear;

    @NotBlank
    private String description;

    @NotEmpty
    private List<Marker> contents;
}
