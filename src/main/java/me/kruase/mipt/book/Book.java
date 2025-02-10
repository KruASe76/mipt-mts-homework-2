package me.kruase.mipt.book;

import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@With
public record Book(
    @Nullable Long id,
    @NotNull String title,
    @NotNull String author,
    @NotNull Long universityCourseId
) {}
