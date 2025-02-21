package me.kruase.mipt.api.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Book entity for creation (without ID)")
public record BookCreateRequest(
        @Schema(example = "Effective Java")
        @NotBlank String title,

        @Schema(example = "Joshua Bloch")
        @NotBlank String author,

        @Schema(example = "69")
        @NotNull @PositiveOrZero Long courseId
) {}
