package me.kruase.mipt.db.course;

import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@With
public record Course(
        @Nullable Long id,
        @NotNull String name,
        @NotNull Integer credits,
        @NotNull Long universityId
) {}
