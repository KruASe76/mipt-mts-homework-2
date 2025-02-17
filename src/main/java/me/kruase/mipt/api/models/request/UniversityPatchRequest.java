package me.kruase.mipt.api.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

@Schema(description = "University entity for partial update (all fields except for the ID are optional)")
public record UniversityPatchRequest(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "MIPT")
        String name,

        @Schema(example = "Dolgoprudny")
        String location
) {
    public Optional<String> nameOptional() {
        return Optional.ofNullable(name);
    }

    public Optional<String> locationOptional() {
        return Optional.ofNullable(location);
    }
}
