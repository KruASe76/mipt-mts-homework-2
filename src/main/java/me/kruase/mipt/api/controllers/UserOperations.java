package me.kruase.mipt.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.kruase.mipt.api.models.request.UserCreateRequest;
import me.kruase.mipt.api.models.request.UserPatchRequest;
import me.kruase.mipt.api.models.request.UserUpdateRequest;
import me.kruase.mipt.api.models.response.UserResponse;
import me.kruase.mipt.db.user.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "Operations with users")
public interface UserOperations {
    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = UserNotFoundException.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUser(@Parameter(description = "User ID") @PathVariable Long id);

    @Operation(summary = "Create user")
    @ApiResponse(responseCode = "201", description = "User created")
    @PostMapping
    ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request);

    @Operation(summary = "Update user")
    @ApiResponse(responseCode = "204", description = "User updated")
    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = UserNotFoundException.class))
    )
    @PutMapping
    ResponseEntity<Void> updateUser(@RequestBody @Valid UserUpdateRequest request);

    @Operation(summary = "Partially update user")
    @ApiResponse(responseCode = "204", description = "User updated")
    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = UserNotFoundException.class))
    )
    @PatchMapping
    ResponseEntity<Void> patchUser(@RequestBody @Valid UserPatchRequest request);

    @Operation(summary = "Delete user by ID")
    @ApiResponse(responseCode = "204", description = "User deleted")
    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = UserNotFoundException.class))
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@Parameter(description = "User ID") @PathVariable Long id);

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<String> handleUserNotFound(UserNotFoundException exception);
}
