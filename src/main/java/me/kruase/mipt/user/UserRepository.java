package me.kruase.mipt.user;

import org.jetbrains.annotations.NotNull;

public interface UserRepository {
    User getById(@NotNull Long id);

    User create(@NotNull User user);

    void update(@NotNull User user);

    void delete(@NotNull Long id);
}
