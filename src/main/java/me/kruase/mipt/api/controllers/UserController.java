package me.kruase.mipt.api.controllers;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.UserCreateRequest;
import me.kruase.mipt.api.models.request.UserPatchRequest;
import me.kruase.mipt.api.models.request.UserUpdateRequest;
import me.kruase.mipt.api.models.response.UserResponse;
import me.kruase.mipt.db.user.User;
import me.kruase.mipt.db.user.exceptions.UserNotFoundException;
import me.kruase.mipt.logic.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController implements UserOperations {
    private final UserService service;

    @Override
    public ResponseEntity<UserResponse> getUser(Long id) {
        User user = service.getById(id);

        UserResponse response = new UserResponse(user.id(), user.email(), user.name(), user.age());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserResponse> createUser(UserCreateRequest request) {
        User user = service.create(request);

        UserResponse response = new UserResponse(user.id(), user.email(), user.name(), user.age());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    public ResponseEntity<Void> updateUser(UserUpdateRequest request) {
        service.update(request);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> patchUser(UserPatchRequest request) {
        service.partialUpdate(request);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}

