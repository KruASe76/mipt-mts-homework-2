package me.kruase.mipt.api.controllers;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.BookCreateRequest;
import me.kruase.mipt.api.models.request.BookPatchRequest;
import me.kruase.mipt.api.models.request.BookUpdateRequest;
import me.kruase.mipt.api.models.response.BookResponse;
import me.kruase.mipt.db.book.Book;
import me.kruase.mipt.logic.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController implements BookOperations {
    private final RateLimiter rateLimiter = RateLimiter.ofDefaults("api");

    private final BookService service;

    @Override
    public ResponseEntity<BookResponse> getBook(Long id) {
        return rateLimiter.executeSupplier(() -> {
            Book book = service.getById(id);

            BookResponse response = new BookResponse(
                    book.id(),
                    book.title(),
                    book.author(),
                    book.courseId()
            );

            return ResponseEntity.ok(response);
        });
    }

    @Override
    public ResponseEntity<BookResponse> createBook(BookCreateRequest request) {
        return rateLimiter.executeSupplier(() -> {
            Book book = service.create(request);

            BookResponse response = new BookResponse(
                    book.id(),
                    book.title(),
                    book.author(),
                    book.courseId()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        });
    }

    @Override
    public ResponseEntity<Void> updateBook(BookUpdateRequest request) {
        return rateLimiter.executeSupplier(() -> {
            service.update(request);

            return ResponseEntity.noContent().build();
        });
    }

    @Override
    public ResponseEntity<Void> patchBook(BookPatchRequest request) {
        return rateLimiter.executeSupplier(() -> {
            service.partialUpdate(request);

            return ResponseEntity.noContent().build();
        });
    }

    @Override
    public ResponseEntity<Void> deleteBook(Long id) {
        return rateLimiter.executeSupplier(() -> {
            service.delete(id);

            return ResponseEntity.noContent().build();
        });
    }
}
