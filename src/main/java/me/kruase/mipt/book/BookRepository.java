package me.kruase.mipt.book;

import org.jetbrains.annotations.NotNull;

public interface BookRepository {
    Book getById(@NotNull Long id);

    void create(@NotNull Book book);

    void update(@NotNull Book book);

    void delete(@NotNull Long id);
}
