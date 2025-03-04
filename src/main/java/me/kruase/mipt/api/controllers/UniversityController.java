package me.kruase.mipt.api.controllers;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.UniversityCreateRequest;
import me.kruase.mipt.api.models.request.UniversityPatchRequest;
import me.kruase.mipt.api.models.request.UniversityUpdateRequest;
import me.kruase.mipt.api.models.response.UniversityResponse;
import me.kruase.mipt.db.university.University;
import me.kruase.mipt.db.university.exceptions.UniversityNotFoundException;
import me.kruase.mipt.logic.services.UniversityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/university")
@RequiredArgsConstructor
public class UniversityController implements UniversityOperations {
    private final UniversityService service;

    @Override
    public ResponseEntity<UniversityResponse> getUniversity(Long id) {
        University university = service.getById(id);

        UniversityResponse response = new UniversityResponse(
                university.id(),
                university.name(),
                university.location()
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UniversityResponse> createUniversity(UniversityCreateRequest request) {
        University university = service.create(request);

        UniversityResponse response = new UniversityResponse(
                university.id(),
                university.name(),
                university.location()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> updateUniversity(UniversityUpdateRequest request) {
        service.update(request);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> patchUniversity(UniversityPatchRequest request) {
        service.partialUpdate(request);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUniversity(Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<String> handleUniversityNotFound(UniversityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
