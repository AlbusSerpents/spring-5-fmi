package com.ddd.books.in.spring.func.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private UUID id;
    private String name;
    private String email;

    private String password;
}
