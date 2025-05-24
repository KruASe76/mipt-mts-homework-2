package me.kruase.mipt.db.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @NotNull Optional<User> findById(@NotNull Long id);

    @NotNull Optional<User> findByEmail(@NotNull String email);
}
