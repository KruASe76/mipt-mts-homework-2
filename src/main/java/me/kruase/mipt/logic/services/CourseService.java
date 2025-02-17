package me.kruase.mipt.logic.services;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.CourseCreateRequest;
import me.kruase.mipt.api.models.request.CoursePatchRequest;
import me.kruase.mipt.api.models.request.CourseUpdateRequest;
import me.kruase.mipt.db.course.Course;
import me.kruase.mipt.db.course.CourseRepository;
import me.kruase.mipt.db.university.UniversityRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final @NotNull CourseRepository repository;
    private final @NotNull UniversityRepository universityRepository;

    public @NotNull Course getById(Long id) {
        return repository.getById(id);
    }

    public @NotNull Course create(CourseCreateRequest request) {
        verifyUniversityId(request.universityId());

        return repository.create(
                new Course(null, request.name(), request.credits(), request.universityId())
        );
    }

    public void update(CourseUpdateRequest request) {
        verifyUniversityId(request.universityId());

        repository.update(
                new Course(request.id(), request.name(), request.credits(), request.universityId())
        );
    }

    public void partialUpdate(CoursePatchRequest request) {
        Course current = getById(request.id());
        request.universityIdOptional().ifPresent(this::verifyUniversityId);

        repository.update(
                current
                        .withName(request.nameOptional().orElse(current.name()))
                        .withCredits(request.creditsOptional().orElse(current.credits()))
                        .withUniversityId(request.universityIdOptional().orElse(current.universityId()))
        );
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    private void verifyUniversityId(Long id) {
        universityRepository.getById(id);
    }
}
