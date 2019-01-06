package com.ddd.books.in.spring.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
