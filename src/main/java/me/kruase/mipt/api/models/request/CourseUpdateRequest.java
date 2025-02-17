package me.kruase.mipt.api.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Book entity for update")
public record CourseUpdateRequest(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "Advanced Java")
        @NotBlank String name,

        @Schema(example = "777")
        @NotNull @PositiveOrZero Integer credits,

        @Schema(example = "69")
        @NotNull @PositiveOrZero Long universityId
) {}
