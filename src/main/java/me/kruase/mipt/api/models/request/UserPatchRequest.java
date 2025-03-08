package me.kruase.mipt.api.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

@Schema(description = "User entity for partial update (all fields except for the ID are optional)")
public record UserPatchRequest(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(format = "email", example = "john@example.com")
        String email,

        @Schema(example = "John")
        String name,

        @Schema(example = "69")
        Integer age
) {
    public Optional<String> emailOptional() {
        return Optional.ofNullable(email);
    }

    public Optional<String> nameOptional() {
        return Optional.ofNullable(name);
    }

    public Optional<Integer> ageOptional() {
        return Optional.ofNullable(age);
    }
}
