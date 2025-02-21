package me.kruase.mipt.api.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Book entity")
public record BookResponse(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "Effective Java")
        @NotBlank String title,

        @Schema(example = "Joshua Bloch")
        @NotBlank String author,

        @Schema(example = "69")
        @NotNull @PositiveOrZero Long courseId
) {}
