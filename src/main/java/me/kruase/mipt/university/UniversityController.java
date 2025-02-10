package me.kruase.mipt.university;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.university.exceptions.UniversityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService service;

    @GetMapping("/api/v1/university/{id}")
    public University getUniversity(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/api/v1/university")
    public void createUniversity(@ModelAttribute University university) {
        service.create(university);
    }

    @PutMapping("/api/v1/university")
    public void updateUniversity(@ModelAttribute University university) {
        service.update(university);
    }

    @DeleteMapping("/api/v1/university/{id}")
    public void deleteUniversity(@PathVariable Long id) {
        service.delete(id);
    }

    @ExceptionHandler(UniversityNotFoundException.class)
    public ResponseEntity<String> handleUniversityNotFound(UniversityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
