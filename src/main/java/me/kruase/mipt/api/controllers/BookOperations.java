package me.kruase.mipt.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.kruase.mipt.api.models.request.BookCreateRequest;
import me.kruase.mipt.api.models.request.BookPatchRequest;
import me.kruase.mipt.api.models.request.BookUpdateRequest;
import me.kruase.mipt.api.models.response.BookResponse;
import me.kruase.mipt.db.book.exceptions.BookNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Book", description = "Operations with books")
public interface BookOperations {
    @Operation(summary = "Get book by ID")
    @ApiResponse(responseCode = "200", description = "Book found")
    @ApiResponse(
            responseCode = "404",
            description = "Book not found",
            content = @Content(schema = @Schema(implementation = BookNotFoundException.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<BookResponse> getBook(@Parameter(description = "Book ID") @PathVariable Long id);

    @Operation(summary = "Create book")
    @ApiResponse(responseCode = "201", description = "Book created")
    @PostMapping
    ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookCreateRequest request);

    @Operation(summary = "Update book")
    @ApiResponse(responseCode = "204", description = "Book updated")
    @ApiResponse(
            responseCode = "404",
            description = "Book not found",
            content = @Content(schema = @Schema(implementation = BookNotFoundException.class))
    )
    @PutMapping
    ResponseEntity<Void> updateBook(@RequestBody @Valid BookUpdateRequest request);

    @Operation(summary = "Partially update book")
    @ApiResponse(responseCode = "204", description = "Book updated")
    @ApiResponse(
            responseCode = "404",
            description = "Book not found",
            content = @Content(schema = @Schema(implementation = BookNotFoundException.class))
    )
    @PatchMapping
    ResponseEntity<Void> patchBook(@RequestBody @Valid BookPatchRequest request);

    @Operation(summary = "Delete book by ID")
    @ApiResponse(responseCode = "204", description = "Book deleted")
    @ApiResponse(
            responseCode = "404",
            description = "Book not found",
            content = @Content(schema = @Schema(implementation = BookNotFoundException.class))
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBook(@Parameter(description = "Book ID") @PathVariable Long id);

    @ExceptionHandler(BookNotFoundException.class)
    ResponseEntity<String> handleBookNotFound(BookNotFoundException exception);
}
