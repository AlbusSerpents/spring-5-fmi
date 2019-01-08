package com.ddd.books.in.spring.auth;

import com.ddd.books.in.spring.func.librarians.Librarian;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import static com.ddd.books.in.spring.auth.PasswordEncoder.encodePassword;
import static com.ddd.books.in.spring.configuration.security.SecurityRole.LIBRARIAN;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;

@Component
public class LibrarianAuthenticationProvider implements AuthenticationProvider {

    private static final String EXPECTED_USERNAME = "lib";
    private static final String EXPECTED_PASSWORD = "1234";
    private static final GrantedAuthority AUTHORITY = new SimpleGrantedAuthority(LIBRARIAN.asRoleAuthorityName());

    @Override
    public Authentication authenticate(final Authentication authentication) {
        if (validateCredentials(authentication)) {
            final Librarian principal = new Librarian(
                    randomUUID(),
                    "name",
                    EXPECTED_USERNAME,
                    EXPECTED_PASSWORD);

            return new UsernamePasswordAuthenticationToken(
                    principal,
                    authentication.getCredentials(),
                    singletonList(AUTHORITY));
        } else {
            final String message = String.format(
                    "Couldn't log in with: %s and credentials: %s",
                    authentication.getName(),
                    authentication.getCredentials());

            throw new BadCredentialsException(message);
        }
    }

    private boolean validateCredentials(final Authentication authentication) {
        final String username = authentication.getName();
        final String password = (String) authentication.getCredentials();
        final String encodedPassword = encodePassword(password);

        return EXPECTED_USERNAME.equals(username) && EXPECTED_PASSWORD.equals(encodedPassword);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
