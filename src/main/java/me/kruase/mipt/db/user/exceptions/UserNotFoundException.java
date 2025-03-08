package me.kruase.mipt.db.user.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "User with id = %d not found";

    public UserNotFoundException(Long id) {
        super(DEFAULT_MESSAGE.formatted(id));
    }
}
