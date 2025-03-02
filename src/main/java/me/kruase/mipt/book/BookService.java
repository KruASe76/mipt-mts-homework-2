package me.kruase.mipt.book;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import me.kruase.mipt.universitycourse.UniversityCourseRepository;

@Service
@RequiredArgsConstructor
public class BookService {
    private final @NotNull BookRepository repository;
    private final @NotNull UniversityCourseRepository universityCourseRepository;

    public Book getById(Long id) {
        return repository.getById(id);
    }

    public Book create(Book book) {
        // Verify course exists before creating book
        universityCourseRepository.getById(book.universityCourseId());

        return repository.create(book);
    }

    public void update(Book book) {
        // Verify course exists before updating book
        universityCourseRepository.getById(book.universityCourseId());

        repository.update(book);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
