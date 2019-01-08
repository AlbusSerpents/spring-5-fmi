package com.ddd.books.in.spring.func.users;

import com.ddd.books.in.spring.auth.AuthenticationService;
import com.ddd.books.in.spring.auth.UserAuthentication;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.ddd.books.in.spring.auth.PasswordEncoder.encodePassword;
import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.*;
import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.*;

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

            throw new FunctionalException(REGISTRATION_FAILED, message, CONFLICT);
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

    public User readById(final UUID userId) {
        return repository
                .findById(userId)
                .orElseThrow(() -> new FunctionalException(MISSING, "No such user exists", NOT_FOUND));
    }

    public User update(
            final UUID id,
            final UpdateUserRequest request) {
        final String newEmail = request.getEmail();

        return repository
                .findById(id)
                .map(user -> user.withEmail(newEmail))
                .map(user -> updatePassword(user, request))
                .map(repository::save)
                .orElseThrow(() -> new FunctionalException(PROFILE_UPDATE_FAILED, "Update failed", BAD_REQUEST));
    }

    private User updatePassword(
            final User user,
            final UpdateUserRequest request) {
        final String oldPassword = request.getOldPassword();
        final String newPassword = request.getNewPassword();

        if (oldPassword == null || newPassword == null) {
            return user;
        } else if (user.getPassword().equals(encodePassword(oldPassword))) {
            return user.withPassword(encodePassword(newPassword));
        } else {
            throw new FunctionalException(PROFILE_UPDATE_FAILED, "Password mismatch", BAD_REQUEST);
        }
    }
}
