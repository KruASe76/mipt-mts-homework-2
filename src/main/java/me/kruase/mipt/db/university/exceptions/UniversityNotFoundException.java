package me.kruase.mipt.db.university.exceptions;

public class UniversityNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "University with id = %d not found";

    public UniversityNotFoundException(Long id) {
        super(DEFAULT_MESSAGE.formatted(id));
    }
}
