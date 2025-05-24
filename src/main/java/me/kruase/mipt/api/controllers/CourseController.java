package me.kruase.mipt.api.controllers;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.CourseCreateRequest;
import me.kruase.mipt.api.models.request.CoursePatchRequest;
import me.kruase.mipt.api.models.request.CourseUpdateRequest;
import me.kruase.mipt.api.models.response.CourseRichResponse;
import me.kruase.mipt.db.course.Course;
import me.kruase.mipt.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController implements CourseOperations {
    private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("api");

    private final CourseService service;

    @Override
    public ResponseEntity<CourseRichResponse> getCourse(Long id) {
        return circuitBreaker.executeSupplier(() -> {
            Course course = service.getRichById(id);

            CourseRichResponse response = CourseRichResponse.from(course);

            return ResponseEntity.ok(response);
        });
    }

    @Override
    public ResponseEntity<CourseRichResponse> createCourse(CourseCreateRequest request) {
        return circuitBreaker.executeSupplier(() -> {
            Course course = service.create(request);

            CourseRichResponse response = CourseRichResponse.from(course);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        });
    }

    @Override
    public ResponseEntity<Void> updateCourse(CourseUpdateRequest request) {
        return circuitBreaker.executeSupplier(() -> {
            service.update(request);

            return ResponseEntity.noContent().build();
        });
    }

    @Override
    public ResponseEntity<Void> patchCourse(CoursePatchRequest request) {
        return circuitBreaker.executeSupplier(() -> {
            service.partialUpdate(request);

            return ResponseEntity.noContent().build();
        });
    }

    @Override
    public ResponseEntity<Void> deleteCourse(Long id) {
        return circuitBreaker.executeSupplier(() -> {
            service.delete(id);

            return ResponseEntity.noContent().build();
        });
    }
}
