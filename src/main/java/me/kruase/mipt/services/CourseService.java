package me.kruase.mipt.services;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.CourseCreateRequest;
import me.kruase.mipt.api.models.request.CoursePatchRequest;
import me.kruase.mipt.api.models.request.CourseUpdateRequest;
import me.kruase.mipt.db.book.Book;
import me.kruase.mipt.db.course.Course;
import me.kruase.mipt.db.course.CourseRepository;
import me.kruase.mipt.db.course.exceptions.CourseNotFoundException;
import me.kruase.mipt.db.university.University;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final @NotNull UniversityService universityService;

    private final @NotNull CourseRepository repository;

    @Transactional(readOnly = true)
    public @NotNull Course getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public @NotNull Course getRichById(Long id) {
        Course course = getById(id);
        University ignoredUniversity = course.getUniversity();
        List<Book> ignoredBooks = course.getBooks();

        return course;
    }

    @Transactional
    public @NotNull Course create(CourseCreateRequest request) {
        University university = universityService.getById(request.universityId());

        Course course = new Course(request.name(), request.credits(), university);
        repository.save(course);

        return getRichById(course.getId());
    }

    @Transactional
    public void update(CourseUpdateRequest request) {
        University university = universityService.getById(request.universityId());

        Course course = getById(request.id());

        course.setName(request.name());
        course.setCredits(request.credits());
        course.setUniversity(university);

        repository.save(course);
    }

    @Async
    @Transactional
    public void partialUpdate(CoursePatchRequest request) {
        Optional<University> university = request.universityIdOptional().map(universityService::getById);

        Course course = getById(request.id());

        course.setName(request.nameOptional().orElse(course.getName()));
        course.setCredits(request.creditsOptional().orElse(course.getCredits()));
        course.setUniversity(university.orElse(course.getUniversity()));

        repository.save(course);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }

        repository.deleteById(id);
    }
}
