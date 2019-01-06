package com.ddd.books.in.spring.configuration.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public enum SecurityRole {
    USER,
    ADMIN;

    public String asUserRole() {
        return name();
    }

    public String asRoleAuthorityName() {
        return "ROLE_" + name();
    }

    public static Optional<SecurityRole> fromAuthority(final GrantedAuthority authority) {
        final String authorityName = authority.getAuthority();
        for (SecurityRole role : SecurityRole.values()) {
            if (role.asRoleAuthorityName().equals(authorityName)) {
                return of(role);
            }
        }
        return empty();
    }
}
