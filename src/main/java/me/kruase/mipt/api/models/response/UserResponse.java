package me.kruase.mipt.api.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import me.kruase.mipt.db.user.User;

@Schema(description = "User entity")
public record UserResponse(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(format = "email", example = "john@example.com")
        @NotBlank @Email String email,

        @Schema(example = "John")
        @NotBlank String name,

        @Schema(example = "69")
        @NotNull Integer age
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getAge());
    }
}
