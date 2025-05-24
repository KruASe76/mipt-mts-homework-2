package me.kruase.mipt.services;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.UserCreateRequest;
import me.kruase.mipt.api.models.request.UserPatchRequest;
import me.kruase.mipt.api.models.request.UserUpdateRequest;
import me.kruase.mipt.db.user.User;
import me.kruase.mipt.db.user.UserRepository;
import me.kruase.mipt.db.user.exceptions.UserNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("SpringCacheableMethodCallsInspection")
@Service
@RequiredArgsConstructor
public class UserService {
    private final @NotNull UserRepository repository;

    @Cacheable(value = "mainCache", key = "#id")
    @Transactional(readOnly = true)
    public @NotNull User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public @NotNull User getRichById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public @NotNull User create(UserCreateRequest request) {
        User user = new User(request.email(), request.name(), request.age());
        repository.save(user);

        return getRichById(user.getId());
    }

    @Transactional
    public void update(UserUpdateRequest request) {
        User user = getById(request.id());

        user.setEmail(request.email());
        user.setName(request.name());
        user.setAge(request.age());

        repository.save(user);
    }

    @Transactional
    public void partialUpdate(UserPatchRequest request) {
        User user = getById(request.id());

        user.setEmail(request.emailOptional().orElse(user.getEmail()));
        user.setName(request.nameOptional().orElse(user.getName()));
        user.setAge(request.ageOptional().orElse(user.getAge()));

        repository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        repository.deleteById(id);
    }
}
