package com.ddd.books.in.spring.auth;

import com.ddd.books.in.spring.configuration.security.SecurityRole;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class AuthenticationService {

    private final AuthenticationProvider usersProvider;
    private final AuthenticationProvider librariansProvider;

    public AuthenticationService(
            final UsersAuthenticationProvider usersProvider,
            final LibrarianAuthenticationProvider librariansProvider) {
        this.usersProvider = usersProvider;
        this.librariansProvider = librariansProvider;
    }

    public UserAuthentication authenticateUser(
            final String email,
            final String password,
            final String sessionId) {
        final Authentication authentication = authenticate(email, password, usersProvider);

        final CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        final Set<SecurityRole> roles = getUserRoles(authentication);

        return new UserAuthentication(sessionId, details.getUser(), roles);
    }

    public LibrarianAuthentication authenticateLibrarian(
            final String username,
            final String password,
            final String sessionId) {
        final Authentication authentication = authenticate(username, password, librariansProvider);

        final CustomLibrarianDetails details = (CustomLibrarianDetails) authentication.getPrincipal();
        final Set<SecurityRole> roles = getUserRoles(authentication);

        return new LibrarianAuthentication(sessionId, details.getLibrarian(), roles);
    }

    private Authentication authenticate(
            final String username,
            final String password,
            final AuthenticationProvider provider) {
        final UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = provider.authenticate(authRequest);
        final SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        return authentication;
    }

    private Set<SecurityRole> getUserRoles(final Authentication authentication) {
        return authentication
                .getAuthorities()
                .stream()
                .map(SecurityRole::fromAuthority)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toSet());
    }
}
