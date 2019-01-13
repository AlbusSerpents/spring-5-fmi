package com.ddd.books.in.spring.func.boxes;

import com.ddd.books.in.spring.func.books.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Box {
    private List<Book> books;
    private String location;
    @Id
    private UUID id;
}
