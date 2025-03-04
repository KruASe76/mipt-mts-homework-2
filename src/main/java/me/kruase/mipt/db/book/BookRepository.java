package me.kruase.mipt.db.book;

import org.jetbrains.annotations.NotNull;

public interface BookRepository {
    @NotNull Book getById(@NotNull Long id);

    @NotNull Book create(@NotNull Book book);

    void update(@NotNull Book book);

    void delete(@NotNull Long id);
}
