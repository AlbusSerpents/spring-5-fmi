package com.ddd.books.in.spring.auth;

import com.ddd.books.in.spring.configuration.security.SecurityRole;
import com.ddd.books.in.spring.func.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserAuthentication {
    private final String sessionId;
    private final User user;
    private final Set<SecurityRole> roles;
}
