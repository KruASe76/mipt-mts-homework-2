package me.kruase.mipt.util;

import me.kruase.mipt.db.book.Book;
import me.kruase.mipt.db.course.Course;
import me.kruase.mipt.db.university.University;
import me.kruase.mipt.db.user.User;

public final class SampleDataUtil {
    public static final University DEFAULT_UNIVERSITY =
            new University("MIPT", "Moscow");

    public static final Course DEFAULT_COURSE =
            new Course("Java", 42, DEFAULT_UNIVERSITY);
    public static final Course NEW_COURSE =
            new Course("Algorithms", 69, DEFAULT_UNIVERSITY);

    public static final Book DEFAULT_BOOK =
            new Book("Effective Java", "Joshua Bloch", DEFAULT_COURSE);
    public static final Book NEW_BOOK =
            new Book("Grokking algorithms", "Aditya Y. Bhargava", NEW_COURSE);

    public static final User DEFAULT_USER =
            new User("email@example.com", "John Doe", 42);
    public static final User NEW_USER =
            new User("new@example.com", "Jane Doe", 69);
}
