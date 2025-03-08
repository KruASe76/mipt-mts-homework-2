package me.kruase.mipt.api.controllers;

import me.kruase.mipt.db.book.exceptions.BookNotFoundException;
import me.kruase.mipt.db.course.exceptions.CourseNotFoundException;
import me.kruase.mipt.db.university.exceptions.UniversityNotFoundException;
import me.kruase.mipt.db.user.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface ExceptionHandlerOperations {
    @ExceptionHandler(BookNotFoundException.class)
    ResponseEntity<String> handleBookNotFound(BookNotFoundException exception);

    @ExceptionHandler(CourseNotFoundException.class)
    ResponseEntity<String> handleCourseNotFound(CourseNotFoundException exception);

    @ExceptionHandler(UniversityNotFoundException.class)
    ResponseEntity<String> handleUniversityNotFound(UniversityNotFoundException exception);

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<String> handleUserNotFound(UserNotFoundException exception);
}
