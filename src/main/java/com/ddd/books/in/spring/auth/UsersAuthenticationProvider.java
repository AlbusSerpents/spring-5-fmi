package com.ddd.books.in.spring.auth;

import com.ddd.books.in.spring.configuration.security.SecurityRole;
import com.ddd.books.in.spring.func.users.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ddd.books.in.spring.auth.PasswordEncoder.encodePassword;
import static com.ddd.books.in.spring.configuration.security.SecurityRole.USER;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;

// @TODO replace this with a real configuration after that
@Component
public class UsersAuthenticationProvider implements AuthenticationProvider {

    private static final String EXPECTED_EMAIL = "pesho@example.com";
    private static final String EXPECTED_PASSWORD = encodePassword("1234");
    private static final List<SecurityRole> ROLES = singletonList(USER);

    @Override
    public Authentication authenticate(final Authentication authentication) {
        if (validateCredentials(authentication)) {
            final User user = new User(
                    randomUUID(),
                    "name",
                    EXPECTED_EMAIL,
                    EXPECTED_PASSWORD);

            final CustomUserDetails principal = new CustomUserDetails(user, ROLES);
            return new UsernamePasswordAuthenticationToken(
                    principal,
                    authentication.getCredentials(),
                    principal.getAuthorities());
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

        return EXPECTED_EMAIL.equals(username) && EXPECTED_PASSWORD.equals(encodedPassword);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
