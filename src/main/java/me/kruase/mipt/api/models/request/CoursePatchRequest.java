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
        Optional<String> name,

        @Schema(example = "777")
        @PositiveOrZero Optional<Integer> credits,

        @Schema(example = "69")
        @PositiveOrZero Optional<Long> universityId
) {}
