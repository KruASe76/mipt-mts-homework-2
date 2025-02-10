package me.kruase.mipt.universitycourse;

import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@With
public record UniversityCourse(
    @Nullable Long id,
    @NotNull String name,
    @NotNull Integer credits,
    @NotNull Long universityId
) {}
