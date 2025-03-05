package me.kruase.mipt.logic.services;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.UniversityCreateRequest;
import me.kruase.mipt.api.models.request.UniversityPatchRequest;
import me.kruase.mipt.api.models.request.UniversityUpdateRequest;
import me.kruase.mipt.db.university.University;
import me.kruase.mipt.db.university.UniversityRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final @NotNull UniversityRepository repository;

    public @NotNull University getById(Long id) {
        return repository.getById(id);
    }

    public @NotNull University create(UniversityCreateRequest request) {
        return repository.create(
                new University(null, request.name(), request.location())
        );
    }

    public void update(UniversityUpdateRequest request) {
        repository.update(
                new University(request.id(), request.name(), request.location())
        );
    }

    public void partialUpdate(UniversityPatchRequest request) {
        University current = getById(request.id());

        repository.update(
                current
                        .withName(request.nameOptional().orElse(current.name()))
                        .withLocation(request.locationOptional().orElse(current.location()))
        );
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
