package me.kruase.mipt.api.controllers;

import me.kruase.mipt.db.book.exceptions.BookNotFoundException;
import me.kruase.mipt.db.course.exceptions.CourseNotFoundException;
import me.kruase.mipt.db.university.exceptions.UniversityNotFoundException;
import me.kruase.mipt.db.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController implements ExceptionHandlerOperations {
    @Override
    public ResponseEntity<String> handleBookNotFound(BookNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @Override
    public ResponseEntity<String> handleCourseNotFound(CourseNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @Override
    public ResponseEntity<String> handleUniversityNotFound(UniversityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @Override
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
