package me.kruase.mipt.university;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final @NotNull UniversityRepository repository;

    public University getById(Long id) {
        return repository.getById(id);
    }

    public void create(University university) {
        repository.create(university);
    }

    public void update(University university) {
        repository.update(university);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
