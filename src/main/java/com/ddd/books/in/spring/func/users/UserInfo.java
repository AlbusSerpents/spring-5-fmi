package com.ddd.books.in.spring.func.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserInfo {
    private UUID id;
    private String name;
    private String email;

}
