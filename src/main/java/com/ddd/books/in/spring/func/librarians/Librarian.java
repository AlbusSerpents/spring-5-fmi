package com.ddd.books.in.spring.func.librarians;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

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

    @JsonProperty(access = WRITE_ONLY)
    private final String password;
}
