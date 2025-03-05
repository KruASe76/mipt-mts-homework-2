package me.kruase.mipt.db.course;

import org.jetbrains.annotations.NotNull;

public interface CourseRepository {
    @NotNull Course getById(@NotNull Long id);

    @NotNull Course create(@NotNull Course course);

    void update(@NotNull Course course);

    void delete(@NotNull Long id);
}

