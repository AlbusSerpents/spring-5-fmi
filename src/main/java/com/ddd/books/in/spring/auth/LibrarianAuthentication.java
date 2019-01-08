package com.ddd.books.in.spring.auth;

import com.ddd.books.in.spring.configuration.security.SecurityRole;
import com.ddd.books.in.spring.func.librarians.Librarian;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class LibrarianAuthentication {
    private final String sessionId;
    private final Librarian librarian;
    private final Set<SecurityRole> roles;
}
