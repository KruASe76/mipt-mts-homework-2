package me.kruase.mipt.api.controllers;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.CourseCreateRequest;
import me.kruase.mipt.api.models.request.CoursePatchRequest;
import me.kruase.mipt.api.models.request.CourseUpdateRequest;
import me.kruase.mipt.api.models.response.CourseResponse;
import me.kruase.mipt.db.course.Course;
import me.kruase.mipt.db.course.exceptions.CourseNotFoundException;
import me.kruase.mipt.logic.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController implements CourseOperations {
    private final CourseService service;

    @Override
    public ResponseEntity<CourseResponse> getCourse(Long id) {
        Course course = service.getById(id);

        CourseResponse response = new CourseResponse(
                course.id(),
                course.name(),
                course.credits(),
                course.universityId()
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CourseResponse> createCourse(CourseCreateRequest request) {
        Course course = service.create(request);

        CourseResponse response = new CourseResponse(
                course.id(),
                course.name(),
                course.credits(),
                course.universityId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> updateCourse(CourseUpdateRequest request) {
        service.update(request);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> patchCourse(CoursePatchRequest request) {
        service.partialUpdate(request);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteCourse(Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<String> handleCourseNotFound(CourseNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
