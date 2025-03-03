package me.kruase.mipt.api.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "User entity for creation (without ID)")
public record UserCreateRequest(
        @Schema(format = "email", example = "john@example.com")
        @NotBlank @Email String email,

        @Schema(example = "John")
        @NotBlank String name,

        @Schema(example = "69")
        @NotNull Integer age
) {}
