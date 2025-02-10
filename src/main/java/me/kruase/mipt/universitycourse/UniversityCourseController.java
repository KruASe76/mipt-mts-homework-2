package me.kruase.mipt.universitycourse;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.universitycourse.exceptions.UniversityCourseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/university-course")
@RequiredArgsConstructor
public class UniversityCourseController {
    private final UniversityCourseService service;

    @GetMapping("/{id}")
    public ResponseEntity<UniversityCourse> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<UniversityCourse> createCourse(@RequestBody UniversityCourse course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(course));
    }

    @PutMapping
    public void updateCourse(@RequestBody UniversityCourse course) {
        service.update(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        service.delete(id);
    }

    @ExceptionHandler(UniversityCourseNotFoundException.class)
    public ResponseEntity<String> handleCourseNotFound(UniversityCourseNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
