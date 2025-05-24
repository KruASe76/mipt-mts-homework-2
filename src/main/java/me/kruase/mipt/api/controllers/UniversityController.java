package me.kruase.mipt.api.controllers;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.UniversityCreateRequest;
import me.kruase.mipt.api.models.request.UniversityPatchRequest;
import me.kruase.mipt.api.models.request.UniversityUpdateRequest;
import me.kruase.mipt.api.models.response.UniversityRichResponse;
import me.kruase.mipt.db.university.University;
import me.kruase.mipt.services.UniversityService;
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
    public ResponseEntity<UniversityRichResponse> getUniversity(Long id) {
        return rateLimiter.executeSupplier(() ->
                circuitBreaker.executeSupplier(() -> {
                    University university = service.getById(id);

                    UniversityRichResponse response = UniversityRichResponse.from(university);

                    return ResponseEntity.ok(response);
                })
        );
    }

    @Override
    public ResponseEntity<UniversityRichResponse> createUniversity(UniversityCreateRequest request) {
        return rateLimiter.executeSupplier(() ->
                circuitBreaker.executeSupplier(() -> {
                    University university = service.create(request);

                    UniversityRichResponse response = UniversityRichResponse.from(university);

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
