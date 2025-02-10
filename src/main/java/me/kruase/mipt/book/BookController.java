package me.kruase.mipt.book;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.book.exceptions.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping("/api/v1/book/{id}")
    public Book getBook(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/api/v1/book")
    public void createBook(@ModelAttribute Book book) {
        service.create(book);
    }

    @PutMapping("/api/v1/book")
    public void updateBook(@ModelAttribute Book book) {
        service.update(book);
    }

    @DeleteMapping("/api/v1/book/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.delete(id);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(BookNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
