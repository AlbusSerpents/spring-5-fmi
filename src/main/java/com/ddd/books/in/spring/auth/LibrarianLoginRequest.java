package com.ddd.books.in.spring.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@NoArgsConstructor
public class LibrarianLoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    private String password;
}
