package me.kruase.mipt.db.user;

import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@With
public record User(
        @Nullable Long id,
        @NotNull String email,
        @NotNull String name,
        @NotNull Integer age
) {}
