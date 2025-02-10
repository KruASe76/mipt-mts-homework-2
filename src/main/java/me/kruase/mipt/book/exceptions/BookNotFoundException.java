package me.kruase.mipt.book.exceptions;

public class BookNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Book with id = %d not found";

    public BookNotFoundException(Long id) {
        super(DEFAULT_MESSAGE.formatted(id));
    }
}
