package com.ddd.books.in.spring.func.boxes;

import com.ddd.books.in.spring.func.books.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Box {
    @Id
    private UUID id;
    private List<BookInBox> books;
    @NotBlank
    private String location;


}
