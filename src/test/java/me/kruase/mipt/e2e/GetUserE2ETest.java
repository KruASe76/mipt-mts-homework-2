package me.kruase.mipt.e2e;

import me.kruase.mipt.api.models.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetUserE2ETest {
    private static final UserResponse dummyUserResponse =
            new UserResponse(0L, "john@example.com", "John", 69);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetUser() {
        String url = "http://localhost:" + port + "/api/v1/user/0";
        ResponseEntity<UserResponse> response = restTemplate.getForEntity(url, UserResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dummyUserResponse, response.getBody());
    }
}
