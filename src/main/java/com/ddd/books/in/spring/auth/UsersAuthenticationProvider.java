package com.ddd.books.in.spring.auth;

import com.ddd.books.in.spring.configuration.security.SecurityRole;
import com.ddd.books.in.spring.func.users.User;
import com.ddd.books.in.spring.func.users.UsersRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.ddd.books.in.spring.auth.PasswordEncoder.encodePassword;
import static com.ddd.books.in.spring.configuration.security.SecurityRole.USER;
import static java.util.Collections.singletonList;

@Component
public class UsersAuthenticationProvider implements AuthenticationProvider {

    private static final List<SecurityRole> ROLES = singletonList(USER);

    private final UsersRepository usersRepository;

    public UsersAuthenticationProvider(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) {

        return findUser(authentication)
                .filter(user -> hasValidCredentials(user, authentication))
                .map(user -> new CustomUserDetails(user, ROLES))
                .map(details -> authenticate(details, authentication))
                .orElseThrow(() -> failure(authentication));
    }

    private Optional<User> findUser(final Authentication authentication) {
        final String email = authentication.getName();
        return usersRepository.findByEmail(email);
    }

    private Authentication authenticate(
            final CustomUserDetails details,
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
            final User user,
            final Authentication authentication) {
        final String password = (String) authentication.getCredentials();
        final String encodedPassword = encodePassword(password);

        final String userPassword = user.getPassword();

        return userPassword.equals(encodedPassword);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
