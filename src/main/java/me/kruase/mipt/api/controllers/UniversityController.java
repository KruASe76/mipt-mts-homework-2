package me.kruase.mipt.api.controllers;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.UniversityCreateRequest;
import me.kruase.mipt.api.models.request.UniversityPatchRequest;
import me.kruase.mipt.api.models.request.UniversityUpdateRequest;
import me.kruase.mipt.api.models.response.UniversityResponse;
import me.kruase.mipt.db.university.University;
import me.kruase.mipt.logic.services.UniversityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/university")
@RequiredArgsConstructor
public class UniversityController implements UniversityOperations {
    private final RateLimiter rateLimiter = RateLimiter.ofDefaults("api");
    private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("api");

    private final UniversityService service;

    @Override
    public ResponseEntity<UniversityResponse> getUniversity(Long id) {
        return rateLimiter.executeSupplier(() ->
                circuitBreaker.executeSupplier(() -> {
                    University university = service.getById(id);

                    UniversityResponse response = new UniversityResponse(
                            university.id(),
                            university.name(),
                            university.location()
                    );

                    return ResponseEntity.ok(response);
                })
        );
    }

    @Override
    public ResponseEntity<UniversityResponse> createUniversity(UniversityCreateRequest request) {
        return rateLimiter.executeSupplier(() ->
                circuitBreaker.executeSupplier(() -> {
                    University university = service.create(request);

                    UniversityResponse response = new UniversityResponse(
                            university.id(),
                            university.name(),
                            university.location()
                    );

                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                })
        );
    }

    @Override
    public ResponseEntity<Void> updateUniversity(UniversityUpdateRequest request) {
        return rateLimiter.executeSupplier(() ->
                circuitBreaker.executeSupplier(() -> {
                    service.update(request);

                    return ResponseEntity.noContent().build();
                })
        );
    }

    @Override
    public ResponseEntity<Void> patchUniversity(UniversityPatchRequest request) {
        return rateLimiter.executeSupplier(() ->
                circuitBreaker.executeSupplier(() -> {
                    service.partialUpdate(request);

                    return ResponseEntity.noContent().build();
                })
        );
    }

    @Override
    public ResponseEntity<Void> deleteUniversity(Long id) {
        return rateLimiter.executeSupplier(() ->
                circuitBreaker.executeSupplier(() -> {
                    service.delete(id);

                    return ResponseEntity.noContent().build();
                })
        );
    }
}
