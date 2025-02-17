package me.kruase.mipt.db.user;

import lombok.extern.slf4j.Slf4j;
import me.kruase.mipt.db.user.exceptions.UserNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private @NotNull User dummyUser = new User(0L, "john@example.com", "John", 69);

    @Override
    public @NotNull User getById(@NotNull Long id) {
        log.info("Get user by id = {}", id);

        if (!id.equals(dummyUser.id())) {
            throw new UserNotFoundException(id);
        }

        return dummyUser;
    }

    @Override
    public @NotNull User create(@NotNull User user) {
        log.info("Create user = {}", user);

        dummyUser = user.withId(dummyUser.id());

        return dummyUser;
    }

    @Override
    public void update(@NotNull User user) {
        log.info("Update user = {}", user);

        if (Objects.equals(user.id(), dummyUser.id())) {
            dummyUser = user;
        } else {
            throw new UserNotFoundException(user.id());
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        log.info("Delete user by id = {}", id);

        if (id.equals(dummyUser.id())) {
            dummyUser =
                    dummyUser
                            .withName("")
                            .withAge(0);
        } else {
            throw new UserNotFoundException(id);
        }
    }
}
