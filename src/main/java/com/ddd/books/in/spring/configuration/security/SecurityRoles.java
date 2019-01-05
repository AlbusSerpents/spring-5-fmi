package com.ddd.books.in.spring.configuration.security;

public enum SecurityRoles {
    USER,
    ADMIN;

    public String asUserRole() {
        return name();
    }

    public String asRoleAuthorityName() {
        return "ROLE_" + name();
    }
}
