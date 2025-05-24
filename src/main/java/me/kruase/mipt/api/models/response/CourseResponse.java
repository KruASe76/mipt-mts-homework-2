package me.kruase.mipt.api.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import me.kruase.mipt.db.course.Course;

@Schema(description = "Course entity")
public record CourseResponse(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "Advanced Java")
        @NotBlank String name,

        @Schema(example = "777")
        @NotNull @PositiveOrZero Integer credits
) {
    public static CourseResponse from(Course course) {
        return new CourseResponse(course.getId(), course.getName(), course.getCredits());
    }
}
