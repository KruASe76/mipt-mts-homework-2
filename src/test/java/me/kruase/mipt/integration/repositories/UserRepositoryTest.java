package me.kruase.mipt.integration.repositories;

import me.kruase.mipt.config.TestcontainersConfig;
import me.kruase.mipt.db.user.User;
import me.kruase.mipt.db.user.UserRepository;
import me.kruase.mipt.util.SampleDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest extends TestcontainersConfig {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserById() {
        User user = new User(
                SampleDataUtil.DEFAULT_USER.getEmail(),
                SampleDataUtil.DEFAULT_USER.getName(),
                SampleDataUtil.DEFAULT_USER.getAge()
        );
        userRepository.save(user);

        User foundUser = userRepository.findById(user.getId()).orElseThrow();
        assertEquals(user, foundUser);

        assertThrows(
                NoSuchElementException.class,
                () -> userRepository.findById(666L).orElseThrow()
        );
    }

    @Test
    public void findUserByEmail() {
        User user = new User(
                UUID.randomUUID() + SampleDataUtil.DEFAULT_USER.getEmail(),
                SampleDataUtil.DEFAULT_USER.getName(),
                SampleDataUtil.DEFAULT_USER.getAge()
        );
        userRepository.save(user);

        User foundUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
        assertEquals(user, foundUser);

        assertThrows(
                NoSuchElementException.class,
                () -> userRepository.findByEmail(UUID.randomUUID() + SampleDataUtil.NEW_USER.getEmail()).orElseThrow()
        );
    }
}
