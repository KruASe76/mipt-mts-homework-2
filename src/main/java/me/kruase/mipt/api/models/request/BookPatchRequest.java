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
        String title,

        @Schema(example = "Joshua Bloch")
        String author,

        @Schema(example = "69")
        @PositiveOrZero Long courseId
) {
    public Optional<String> titleOptional() {
        return Optional.ofNullable(title);
    }

    public Optional<String> authorOptional() {
        return Optional.ofNullable(author);
    }

    public Optional<Long> courseIdOptional() {
        return Optional.ofNullable(courseId);
    }
}
