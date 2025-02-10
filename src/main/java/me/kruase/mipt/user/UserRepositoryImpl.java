package me.kruase.mipt.user;

import lombok.extern.slf4j.Slf4j;
import me.kruase.mipt.user.exceptions.UserNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private @NotNull User dummyUser = new User(0L, "Dummy", 42);

    @Override
    public User getById(@NotNull Long id) {
        log.info("Get user by id = {}", id);

        if (!id.equals(dummyUser.id())) {
            throw new UserNotFoundException(id);
        }

        return dummyUser;
    }

    @Override
    public void create(@NotNull User user) {
        log.info("Create user = {}", user);

        dummyUser = user.withId(dummyUser.id());
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
