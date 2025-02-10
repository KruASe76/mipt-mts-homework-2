package me.kruase.mipt.user;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final @NotNull UserRepository repository;

    public User getById(Long id) {
        return repository.getById(id);
    }

    public void create(User user) {
        repository.create(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
