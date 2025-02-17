package me.kruase.mipt.db.course.exceptions;

public class CourseNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Course with id = %d not found";

    public CourseNotFoundException(Long id) {
        super(DEFAULT_MESSAGE.formatted(id));
    }
}
