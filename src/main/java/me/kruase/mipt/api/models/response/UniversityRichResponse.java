package me.kruase.mipt.api.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import me.kruase.mipt.db.university.University;

import java.util.List;


@Schema(description = "University entity with courses")
public record UniversityRichResponse(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "MIPT")
        @NotBlank String name,

        @Schema(example = "Dolgoprudny")
        @NotBlank String location,

        @Schema
        @NotNull List<CourseResponse> courses
) {
    public static UniversityRichResponse from(University university) {
        return new UniversityRichResponse(
                university.getId(),
                university.getName(),
                university.getLocation(),
                university.getCourses().stream().map(CourseResponse::from).toList()
        );
    }
}
