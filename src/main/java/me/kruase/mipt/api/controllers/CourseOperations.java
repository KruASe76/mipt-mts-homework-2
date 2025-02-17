package me.kruase.mipt.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.kruase.mipt.api.models.request.CourseCreateRequest;
import me.kruase.mipt.api.models.request.CoursePatchRequest;
import me.kruase.mipt.api.models.request.CourseUpdateRequest;
import me.kruase.mipt.api.models.response.CourseResponse;
import me.kruase.mipt.db.course.exceptions.CourseNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Course", description = "Operations with courses")
public interface CourseOperations {
    @Operation(summary = "Get course by ID")
    @ApiResponse(responseCode = "200", description = "Course found")
    @ApiResponse(
            responseCode = "404",
            description = "Course not found",
            content = @Content(schema = @Schema(implementation = CourseNotFoundException.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<CourseResponse> getCourse(@Parameter(description = "Course ID") @PathVariable Long id);

    @Operation(summary = "Create course")
    @ApiResponse(responseCode = "201", description = "Course created")
    @ApiResponse(
            responseCode = "404",
            description = "Course not found",
            content = @Content(schema = @Schema(implementation = CourseNotFoundException.class))
    )
    @PostMapping
    ResponseEntity<CourseResponse> createCourse(@RequestBody @Valid CourseCreateRequest request);

    @Operation(summary = "Update course")
    @ApiResponse(responseCode = "204", description = "Course updated")
    @ApiResponse(
            responseCode = "404",
            description = "Course not found",
            content = @Content(schema = @Schema(implementation = CourseNotFoundException.class))
    )
    @PutMapping
    ResponseEntity<Void> updateCourse(@RequestBody @Valid CourseUpdateRequest request);

    @Operation(summary = "Partially update course")
    @ApiResponse(responseCode = "204", description = "Course updated")
    @ApiResponse(
            responseCode = "404",
            description = "Course not found",
            content = @Content(schema = @Schema(implementation = CourseNotFoundException.class))
    )
    @PatchMapping
    ResponseEntity<Void> patchCourse(@RequestBody @Valid CoursePatchRequest request);

    @Operation(summary = "Delete course by ID")
    @ApiResponse(responseCode = "204", description = "Course deleted")
    @ApiResponse(
            responseCode = "404",
            description = "Course not found",
            content = @Content(schema = @Schema(implementation = CourseNotFoundException.class))
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCourse(@Parameter(description = "Course ID") @PathVariable Long id);

    @ExceptionHandler(CourseNotFoundException.class)
    ResponseEntity<String> handleCourseNotFound(CourseNotFoundException exception);
}
