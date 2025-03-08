package me.kruase.mipt.logic.services;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.BookCreateRequest;
import me.kruase.mipt.api.models.request.BookPatchRequest;
import me.kruase.mipt.api.models.request.BookUpdateRequest;
import me.kruase.mipt.db.book.Book;
import me.kruase.mipt.db.book.BookRepository;
import me.kruase.mipt.db.course.CourseRepository;
import me.kruase.mipt.db.course.exceptions.CourseNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final @NotNull BookRepository repository;
    private final @NotNull CourseRepository courseRepository;

    public @NotNull Book getById(Long id) {
        return repository.getById(id);
    }

    public @NotNull Book create(BookCreateRequest request) {
        verifyCourseId(request.courseId());

        return repository.create(
                new Book(null, request.title(), request.author(), request.courseId())
        );
    }

    public void update(BookUpdateRequest request) {
        verifyCourseId(request.courseId());

        repository.update(
                new Book(request.id(), request.title(), request.author(), request.courseId())
        );
    }

    // it is possible that creation of the Course was stuck in a different thread,
    // but the Book is being created immediately after
    @Retryable(
            retryFor = CourseNotFoundException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 10000)
    )
    public void partialUpdate(BookPatchRequest request) {
        Book current = getById(request.id());
        request.courseIdOptional().ifPresent(this::verifyCourseId);

        repository.update(
                current
                        .withTitle(request.titleOptional().orElse(current.title()))
                        .withAuthor(request.authorOptional().orElse(current.author()))
                        .withCourseId(request.courseIdOptional().orElse(current.courseId()))
        );
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    private void verifyCourseId(Long id) {
        courseRepository.getById(id);
    }
}
