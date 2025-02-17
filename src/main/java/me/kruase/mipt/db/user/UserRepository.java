package me.kruase.mipt.db.user;

import org.jetbrains.annotations.NotNull;

public interface UserRepository {
    @NotNull User getById(@NotNull Long id);

    @NotNull User create(@NotNull User user);

    void update(@NotNull User user);

    void delete(@NotNull Long id);
}
