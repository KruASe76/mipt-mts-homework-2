package me.kruase.mipt.user;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/api/v1/user/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/api/v1/user")
    public void createUser(@ModelAttribute User user) {
        service.create(user);
    }

    @PutMapping("/api/v1/user")
    public void updateUser(@ModelAttribute User user) {
        service.update(user);
    }

    @DeleteMapping("api/v1/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.delete(id);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
