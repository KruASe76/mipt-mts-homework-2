package me.kruase.mipt.api.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "University entity for update")
public record UniversityUpdateRequest(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "MIPT")
        @NotBlank String name,

        @Schema(example = "Dolgoprudny")
        @NotBlank String location
) {}
