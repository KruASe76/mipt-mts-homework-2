package me.kruase.mipt.book;

import lombok.extern.slf4j.Slf4j;
import me.kruase.mipt.book.exceptions.BookNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import java.util.Objects;

@Repository
@Slf4j
public class BookRepositoryImpl implements BookRepository {
    private @NotNull Book dummyBook = new Book(0L, "Effective Java", "Joshua Bloch", 0L);

    @Override
    public Book getById(@NotNull Long id) {
        log.info("Get book by id = {}", id);
        if (!id.equals(dummyBook.id())) {
            throw new BookNotFoundException(id);
        }
        return dummyBook;
    }

    @Override
    public void create(@NotNull Book book) {
        log.info("Create book = {}", book);
        dummyBook = book.withId(dummyBook.id());
    }

    @Override
    public void update(@NotNull Book book) {
        log.info("Update book = {}", book);
        if (Objects.equals(book.id(), dummyBook.id())) {
            dummyBook = book;
        } else {
            throw new BookNotFoundException(book.id());
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        log.info("Delete book by id = {}", id);
        if (id.equals(dummyBook.id())) {
            dummyBook = dummyBook
                    .withTitle("")
                    .withAuthor("")
                    .withUniversityCourseId(0L);
        } else {
            throw new BookNotFoundException(id);
        }
    }
}
