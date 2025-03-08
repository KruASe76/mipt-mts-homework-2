package me.kruase.mipt.api.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "University entity for creation (without ID)")
public record UniversityCreateRequest(
        @Schema(example = "MIPT")
        @NotBlank String name,

        @Schema(example = "Dolgoprudny")
        @NotBlank String location
) {}
