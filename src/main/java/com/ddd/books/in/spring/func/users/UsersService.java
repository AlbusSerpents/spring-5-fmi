package com.ddd.books.in.spring.func.users;

import com.ddd.books.in.spring.auth.AuthenticationService;
import com.ddd.books.in.spring.auth.UserAuthentication;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.stereotype.Service;

import static com.ddd.books.in.spring.auth.PasswordEncoder.encodePassword;
import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.REGISTRATION_FAILED;
import static java.util.UUID.randomUUID;

@Service
public class UsersService {

    private final UsersRepository repository;
    private final AuthenticationService authService;

    public UsersService(
            final UsersRepository repository,
            final AuthenticationService authService) {
        this.repository = repository;
        this.authService = authService;
    }

    public UserAuthentication register(
            final RegistrationRequest request,
            final String sessionId) {
        final String name = request.getName();
        final String email = request.getEmail();

        if (areTaken(name, email)) {
            final String message = String.format(
                    "User with name %s or email %s already exists",
                    name, email);

            throw new FunctionalException(REGISTRATION_FAILED, message);
        }

        final String password = request.getPassword();

        final String encodedPassword = encodePassword(password);
        final User user = new User(randomUUID(), name, email, encodedPassword);
        final User registered = repository.insert(user);

        return authService.authenticateUser(registered.getEmail(), password, sessionId);
    }

    private boolean areTaken(final String name, final String email) {
        return repository
                .findByExisting(name, email)
                .findAny()
                .isPresent();
    }
}
