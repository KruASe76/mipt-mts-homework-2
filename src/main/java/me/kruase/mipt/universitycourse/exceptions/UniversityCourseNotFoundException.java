package me.kruase.mipt.universitycourse.exceptions;

public class UniversityCourseNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "University course with id = %d not found";

    public UniversityCourseNotFoundException(Long id) {
        super(DEFAULT_MESSAGE.formatted(id));
    }
}
