package me.kruase.mipt.api.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import me.kruase.mipt.db.book.Book;

@Schema(description = "Book entity with course")
public record BookRichResponse(
        @Schema(example = "42")
        @NotNull @PositiveOrZero Long id,

        @Schema(example = "Effective Java")
        @NotBlank String title,

        @Schema(example = "Joshua Bloch")
        @NotBlank String author,

        @Schema
        @NotNull CourseResponse course
) {
    public static BookRichResponse from(Book book) {
        return new BookRichResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                CourseResponse.from(book.getCourse())
        );
    }
}
