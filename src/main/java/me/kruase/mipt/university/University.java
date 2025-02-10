package me.kruase.mipt.university;

import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@With
public record University(@Nullable Long id, @NotNull String name, @NotNull String location) {}
