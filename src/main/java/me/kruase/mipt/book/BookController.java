package me.kruase.mipt.book;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.book.exceptions.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(book));
    }

    @PutMapping
    public void updateBook(@RequestBody Book book) {
        service.update(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.delete(id);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(BookNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
