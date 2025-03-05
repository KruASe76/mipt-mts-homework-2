package me.kruase.mipt.api.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

@Schema(description = "Course entity for partial update (all fields except for the ID are optional)")
public record CoursePatchRequest(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "Advanced Java")
        String name,

        @Schema(example = "777")
        @PositiveOrZero Integer credits,

        @Schema(example = "69")
        @PositiveOrZero Long universityId
) {
    public Optional<String> nameOptional() {
        return Optional.ofNullable(name);
    }

    public Optional<Integer> creditsOptional() {
        return Optional.ofNullable(credits);
    }

    public Optional<Long> universityIdOptional() {
        return Optional.ofNullable(universityId);
    }
}
