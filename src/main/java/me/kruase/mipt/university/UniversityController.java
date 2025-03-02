package me.kruase.mipt.university;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.university.exceptions.UniversityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/university")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService service;

    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversity(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<University> createUniversity(@RequestBody University university) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(university));
    }

    @PutMapping
    public void updateUniversity(@RequestBody University university) {
        service.update(university);
    }

    @DeleteMapping("/{id}")
    public void deleteUniversity(@PathVariable Long id) {
        service.delete(id);
    }

    @ExceptionHandler(UniversityNotFoundException.class)
    public ResponseEntity<String> handleUniversityNotFound(UniversityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
