package com.ddd.books.in.spring.func.users;

import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.stereotype.Service;

import static com.ddd.books.in.spring.auth.PasswordEncoder.encodePassword;
import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.REGISTRATION_FAILED;
import static java.util.UUID.randomUUID;

@Service
public class UsersService {

    private final UsersRepository repository;

    public UsersService(final UsersRepository repository) {
        this.repository = repository;
    }

    public User register(final RegistrationRequest request) {
        final String name = request.getName();
        final String email = request.getEmail();

        if (areTaken(name, email)) {
            final String message = String.format(
                    "User with name %s or email %s already exists",
                    name, email);

            throw new FunctionalException(REGISTRATION_FAILED, message);
        }

        final String encodedPassword = encodePassword(request.getPassword());
        final User user = new User(randomUUID(), name, email, encodedPassword);
        return repository.insert(user);
    }

    private boolean areTaken(final String name, final String email) {
        return repository
                .findByExisting(name, email)
                .findAny()
                .isPresent();
    }
}
