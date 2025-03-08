package me.kruase.mipt.db.university;

import org.jetbrains.annotations.NotNull;

public interface UniversityRepository {
    @NotNull University getById(@NotNull Long id);

    @NotNull University create(@NotNull University university);

    void update(@NotNull University university);

    void delete(@NotNull Long id);
}

