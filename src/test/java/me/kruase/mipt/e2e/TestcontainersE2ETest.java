package me.kruase.mipt.e2e;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testcontainers")
public class TestcontainersE2ETest {
    @SuppressWarnings("resource")
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("test")
                    .withUsername("test-user")
                    .withPassword("test-password")
                    .withInitScript("db/init/test.sql");

    // Lombok @Slf4j annotation doesn't work :(
    private static final Logger log = LoggerFactory.getLogger(TestcontainersE2ETest.class);

    static {
        postgresContainer.start();
    }

    @Test
    public void testDatabaseConnection() {
        log.info(
                "host: '{}', port: {}",
                postgresContainer.getHost(),
                postgresContainer.getFirstMappedPort()
        );
        log.info("url: {}", postgresContainer.getJdbcUrl());

        assertTrue(postgresContainer.getJdbcUrl().contains(postgresContainer.getHost()));
        assertTrue(
                postgresContainer.getJdbcUrl()
                        .contains(postgresContainer.getFirstMappedPort().toString())
        );
    }
}
