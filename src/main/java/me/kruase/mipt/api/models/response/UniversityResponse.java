package me.kruase.mipt.api.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import me.kruase.mipt.db.university.University;


@Schema(description = "University entity")
public record UniversityResponse(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "MIPT")
        @NotBlank String name,

        @Schema(example = "Dolgoprudny")
        @NotBlank String location
) {
    public static UniversityResponse from(University university) {
        return new UniversityResponse(
                university.getId(),
                university.getName(),
                university.getLocation()
        );
    }
}
