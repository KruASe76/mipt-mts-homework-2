package me.kruase.mipt.logic.services;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.UserCreateRequest;
import me.kruase.mipt.api.models.request.UserPatchRequest;
import me.kruase.mipt.api.models.request.UserUpdateRequest;
import me.kruase.mipt.db.user.User;
import me.kruase.mipt.db.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final @NotNull UserRepository repository;

    @Cacheable(value = "mainCache", key = "#id")
    public @NotNull User getById(Long id) {
        return repository.getById(id);
    }

    public @NotNull User create(UserCreateRequest request) {
        return repository.create(
                new User(null, request.email(), request.name(), request.age())
        );
    }

    public void update(UserUpdateRequest request) {
        repository.update(
                new User(request.id(), request.email(), request.name(), request.age())
        );
    }

    public void partialUpdate(UserPatchRequest request) {
        User current = repository.getById(request.id());

        repository.update(
                current
                        .withEmail(request.emailOptional().orElse(current.email()))
                        .withName(request.nameOptional().orElse(current.name()))
                        .withAge(request.ageOptional().orElse(current.age()))
        );
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
