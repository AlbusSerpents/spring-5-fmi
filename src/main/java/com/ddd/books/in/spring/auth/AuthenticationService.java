package com.ddd.books.in.spring.auth;

import com.ddd.books.in.spring.configuration.security.SecurityRole;
import com.ddd.books.in.spring.func.users.User;
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

    private final UsersAuthenticationProvider usersProvider;

    public AuthenticationService(
            final UsersAuthenticationProvider usersProvider) {
        this.usersProvider = usersProvider;
    }

    public UserAuthentication authenticateUser(
            final String username,
            final String password,
            final String sessionId) {
        final Authentication authentication = authenticate(username, password);

        final User user = (User) authentication.getPrincipal();
        final Set<SecurityRole> roles = getUserRoles(authentication);

        return new UserAuthentication(sessionId, user, roles);
    }

    private Authentication authenticate(final String username, final String password) {
        final UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = usersProvider.authenticate(authRequest);
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
