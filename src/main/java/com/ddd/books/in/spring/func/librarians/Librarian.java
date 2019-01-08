package com.ddd.books.in.spring.func.librarians;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
@AllArgsConstructor
public class Librarian {
    @Id
    private final UUID id;

    @Indexed(unique = true)
    private final String name;
    @Indexed(unique = true)
    private final String username;

    private final String password;
}
