package com.ddd.books.in.spring.func.boxes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class BookInBox {
    @Id
    public String id;
    @NotBlank
    private String boxId;
    @NotBlank
    private String name;
    @NotBlank
    private String author;
    @Positive
    private Integer publishingYear;
    @NotBlank
    private String bookCover;


    private boolean isTaken = false;
}
