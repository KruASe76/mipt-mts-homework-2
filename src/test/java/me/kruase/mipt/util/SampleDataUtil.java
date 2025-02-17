package me.kruase.mipt.util;

import me.kruase.mipt.db.book.Book;

public final class SampleDataUtil {
    public static final Book DEFAULT_BOOK =
            new Book(0L, "Effective Java", "Joshua Bloch", 0L);
    public static final Book NEW_BOOK =
            new Book(1L, "Grokking algorithms", "Aditya Y. Bhargava", 1L);
}
