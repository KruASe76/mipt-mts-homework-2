package me.kruase.mipt.universitycourse;

import org.jetbrains.annotations.NotNull;

public interface UniversityCourseRepository {
    UniversityCourse getById(@NotNull Long id);

    UniversityCourse create(@NotNull UniversityCourse course);

    void update(@NotNull UniversityCourse course);

    void delete(@NotNull Long id);
}
