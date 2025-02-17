package me.kruase.mipt.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.kruase.mipt.api.models.request.UniversityCreateRequest;
import me.kruase.mipt.api.models.request.UniversityPatchRequest;
import me.kruase.mipt.api.models.request.UniversityUpdateRequest;
import me.kruase.mipt.api.models.response.UniversityResponse;
import me.kruase.mipt.db.university.exceptions.UniversityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "University", description = "Operations with universities")
public interface UniversityOperations {
    @Operation(summary = "Get university by ID")
    @ApiResponse(responseCode = "200", description = "University found")
    @ApiResponse(
            responseCode = "404",
            description = "University not found",
            content = @Content(schema = @Schema(implementation = UniversityNotFoundException.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<UniversityResponse> getUniversity(@Parameter(description = "University ID") @PathVariable Long id);

    @Operation(summary = "Create university")
    @ApiResponse(responseCode = "201", description = "University created")
    @ApiResponse(
            responseCode = "404",
            description = "University not found",
            content = @Content(schema = @Schema(implementation = UniversityNotFoundException.class))
    )
    @PostMapping
    ResponseEntity<UniversityResponse> createUniversity(@RequestBody @Valid UniversityCreateRequest request);

    @Operation(summary = "Update university")
    @ApiResponse(responseCode = "204", description = "University updated")
    @ApiResponse(
            responseCode = "404",
            description = "University not found",
            content = @Content(schema = @Schema(implementation = UniversityNotFoundException.class))
    )
    @PutMapping
    ResponseEntity<Void> updateUniversity(@RequestBody @Valid UniversityUpdateRequest request);

    @Operation(summary = "Partially update university")
    @ApiResponse(responseCode = "204", description = "University updated")
    @ApiResponse(
            responseCode = "404",
            description = "University not found",
            content = @Content(schema = @Schema(implementation = UniversityNotFoundException.class))
    )
    @PatchMapping
    ResponseEntity<Void> patchUniversity(@RequestBody @Valid UniversityPatchRequest request);

    @Operation(summary = "Delete university by ID")
    @ApiResponse(responseCode = "204", description = "University deleted")
    @ApiResponse(
            responseCode = "404",
            description = "University not found",
            content = @Content(schema = @Schema(implementation = UniversityNotFoundException.class))
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUniversity(@Parameter(description = "University ID") @PathVariable Long id);

    @ExceptionHandler(UniversityNotFoundException.class)
    ResponseEntity<String> handleUniversityNotFound(UniversityNotFoundException exception);
}
