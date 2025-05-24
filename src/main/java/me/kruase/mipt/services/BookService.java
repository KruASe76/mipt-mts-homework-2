package me.kruase.mipt.services;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.BookCreateRequest;
import me.kruase.mipt.api.models.request.BookPatchRequest;
import me.kruase.mipt.api.models.request.BookUpdateRequest;
import me.kruase.mipt.db.book.Book;
import me.kruase.mipt.db.book.BookRepository;
import me.kruase.mipt.db.book.exceptions.BookNotFoundException;
import me.kruase.mipt.db.course.Course;
import me.kruase.mipt.db.course.exceptions.CourseNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final @NotNull BookRepository repository;

    private final @NotNull CourseService courseService;

    @Transactional(readOnly = true)
    public @NotNull Book getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public @NotNull Book getRichById(Long id) {
        Book book = getById(id);
        Course ignored = book.getCourse();

        return book;
    }

    @Transactional
    public @NotNull Book create(BookCreateRequest request) {
        Course course = courseService.getById(request.courseId());

        Book newBook = new Book(request.title(), request.author(), course);
        repository.save(newBook);

        return getRichById(newBook.getId());
    }

    @Transactional
    public void update(BookUpdateRequest request) {
        Course course = courseService.getById(request.courseId());

        Book book = getById(request.id());

        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCourse(course);

        repository.save(book);
    }

    // it is possible that creation of the Course was stuck in a different thread,
    // but the Book is being created immediately after
    @Retryable(
            retryFor = CourseNotFoundException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 10000)
    )
    @Transactional
    public void partialUpdate(BookPatchRequest request) {
        Optional<Course> course = request.courseIdOptional().map(courseService::getById);

        Book book = getById(request.id());

        book.setTitle(request.titleOptional().orElse(book.getTitle()));
        book.setAuthor(request.authorOptional().orElse(book.getAuthor()));
        book.setCourse(course.orElse(book.getCourse()));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BookNotFoundException(id);
        }

        repository.deleteById(id);
    }
}
