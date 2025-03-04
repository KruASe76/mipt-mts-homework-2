package me.kruase.mipt.db.book;

import lombok.extern.slf4j.Slf4j;
import me.kruase.mipt.db.book.exceptions.BookNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import java.util.Objects;

@Repository
@Slf4j
public class BookRepositoryImpl implements BookRepository {
    private @NotNull Book dummyBook = new Book(0L, "Effective Java", "Joshua Bloch", 0L);

    @Override
    public @NotNull Book getById(@NotNull Long id) {
        log.info("Getting book by id = {}", id);
        if (!id.equals(dummyBook.id())) {
            throw new BookNotFoundException(id);
        }
        return dummyBook;
    }

    @Override
    public @NotNull Book create(@NotNull Book book) {
        log.info("Creating book = {}", book);
        dummyBook = book.withId(dummyBook.id());
        return dummyBook;
    }

    @Override
    public void update(@NotNull Book book) {
        log.info("Updating book = {}", book);
        if (Objects.equals(book.id(), dummyBook.id())) {
            dummyBook = book;
        } else {
            throw new BookNotFoundException(book.id());
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        log.info("Deleting book by id = {}", id);
        if (id.equals(dummyBook.id())) {
            dummyBook = dummyBook
                    .withTitle("")
                    .withAuthor("")
                    .withCourseId(0L);
        } else {
            throw new BookNotFoundException(id);
        }
    }
}

