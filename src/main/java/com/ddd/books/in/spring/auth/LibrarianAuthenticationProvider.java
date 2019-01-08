package com.ddd.books.in.spring.auth;

import com.ddd.books.in.spring.configuration.security.SecurityRole;
import com.ddd.books.in.spring.func.librarians.Librarian;
import com.ddd.books.in.spring.func.librarians.LibrariansRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.ddd.books.in.spring.auth.PasswordEncoder.encodePassword;
import static com.ddd.books.in.spring.configuration.security.SecurityRole.LIBRARIAN;
import static java.util.Collections.singletonList;

@Component
public class LibrarianAuthenticationProvider implements AuthenticationProvider {

    private static final List<SecurityRole> ROLES = singletonList(LIBRARIAN);

    private final LibrariansRepository librariansRepository;

    public LibrarianAuthenticationProvider(final LibrariansRepository librariansRepository) {
        this.librariansRepository = librariansRepository;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) {
        return findLibrarian(authentication)
                .filter(librarian -> hasValidCredentials(librarian, authentication))
                .map(librarian -> new CustomLibrarianDetails(librarian, ROLES))
                .map(details -> authenticate(details, authentication))
                .orElseThrow(() -> failure(authentication));
    }

    private Optional<Librarian> findLibrarian(final Authentication authentication) {
        final String username = authentication.getName();
        return librariansRepository.findByUsername(username);
    }

    private Authentication authenticate(
            final CustomLibrarianDetails details,
            final Authentication authentication) {
        return new UsernamePasswordAuthenticationToken(
                details,
                authentication.getCredentials(),
                details.getAuthorities());
    }

    private BadCredentialsException failure(final Authentication authentication) {
        final String message = String.format(
                "Couldn't log in with: %s and credentials: %s",
                authentication.getName(),
                authentication.getCredentials());

        return new BadCredentialsException(message);
    }

    private boolean hasValidCredentials(
            final Librarian librarian,
            final Authentication authentication) {
        final String password = (String) authentication.getCredentials();
        final String encodedPassword = encodePassword(password);

        final String librarianPassword = librarian.getPassword();
        return librarianPassword.equals(encodedPassword);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
