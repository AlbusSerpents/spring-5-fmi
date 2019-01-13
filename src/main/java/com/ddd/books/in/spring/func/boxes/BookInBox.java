package com.ddd.books.in.spring.func.boxes;


import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.ws.rs.DefaultValue;
import java.util.UUID;

public class BookInBox{
    @Id
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String author;
    @Positive
    private Integer publishingYear;

    private boolean isTaken = false;

    public BookInBox() {
    }
}
