package me.kruase.mipt.university;

import org.jetbrains.annotations.NotNull;

public interface UniversityRepository {
    University getById(@NotNull Long id);

    void create(@NotNull University university);

    void update(@NotNull University university);

    void delete(@NotNull Long id);
}
