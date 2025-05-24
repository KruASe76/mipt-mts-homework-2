package me.kruase.mipt.api.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import me.kruase.mipt.db.course.Course;

import java.util.List;

@Schema(description = "Course entity")
public record CourseRichResponse(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "Advanced Java")
        @NotBlank String name,

        @Schema(example = "777")
        @NotNull @PositiveOrZero Integer credits,

        @Schema
        @NotNull UniversityResponse university,

        @Schema
        @NotNull List<BookResponse> books
) {
    public static CourseRichResponse from(Course course) {
        return new CourseRichResponse(
                course.getId(),
                course.getName(),
                course.getCredits(),
                UniversityResponse.from(course.getUniversity()),
                course.getBooks().stream().map(BookResponse::from).toList()
        );
    }
}
