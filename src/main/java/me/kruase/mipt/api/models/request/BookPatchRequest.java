package me.kruase.mipt.api.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

@Schema(description = "Book entity for partial update (all fields except for the ID are optional)")
public record BookPatchRequest(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "Effective Java")
        Optional<String> title,

        @Schema(example = "Joshua Bloch")
        Optional<String> author,

        @Schema(example = "69")
        @PositiveOrZero Optional<Long> courseId
) {}
