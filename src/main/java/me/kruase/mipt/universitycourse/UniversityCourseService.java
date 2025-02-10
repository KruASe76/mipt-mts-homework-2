package me.kruase.mipt.universitycourse;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.university.UniversityRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityCourseService {
    private final @NotNull UniversityCourseRepository repository;
    private final @NotNull UniversityRepository universityRepository;

    public UniversityCourse getById(Long id) {
        return repository.getById(id);
    }

    public void create(UniversityCourse course) {
        // Verify university exists before creating course
        universityRepository.getById(course.universityId());

        repository.create(course);
    }

    public void update(UniversityCourse course) {
        // Verify university exists before updating course
        universityRepository.getById(course.universityId());

        repository.update(course);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
