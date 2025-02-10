package me.kruase.mipt.universitycourse;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.universitycourse.exceptions.UniversityCourseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UniversityCourseController {
    private final UniversityCourseService service;

    @GetMapping("/api/v1/university-course/{id}")
    public UniversityCourse getCourse(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/api/v1/university-course")
    public void createCourse(@ModelAttribute UniversityCourse course) {
        service.create(course);
    }

    @PutMapping("/api/v1/university-course")
    public void updateCourse(@ModelAttribute UniversityCourse course) {
        service.update(course);
    }

    @DeleteMapping("/api/v1/university-course/{id}")
    public void deleteCourse(@PathVariable Long id) {
        service.delete(id);
    }

    @ExceptionHandler(UniversityCourseNotFoundException.class)
    public ResponseEntity<String> handleCourseNotFound(UniversityCourseNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
