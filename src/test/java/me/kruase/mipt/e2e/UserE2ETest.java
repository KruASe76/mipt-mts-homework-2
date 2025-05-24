package me.kruase.mipt.e2e;

import jakarta.validation.constraints.NotNull;
import me.kruase.mipt.api.models.request.UserCreateRequest;
import me.kruase.mipt.api.models.request.UserUpdateRequest;
import me.kruase.mipt.api.models.response.UserResponse;
import me.kruase.mipt.config.TestcontainersConfig;
import me.kruase.mipt.db.user.User;
import me.kruase.mipt.util.SampleDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserE2ETest extends TestcontainersConfig {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static void assertUserFieldsEqual(@NotNull User expected, @NotNull UserResponse actual) {
        assertEquals(expected.getEmail(), actual.email());
        assertEquals(expected.getName(), actual.name());
        assertEquals(expected.getAge(), actual.age());
    }

    @Test
    public void testUserE2E() {
        String baseUrl = "http://localhost:" + port + "/api/v1/user";

        // create
        ResponseEntity<UserResponse> postResponse =
                restTemplate.postForEntity(
                        baseUrl,
                        new UserCreateRequest(
                                SampleDataUtil.DEFAULT_USER.getEmail(),
                                SampleDataUtil.DEFAULT_USER.getName(),
                                SampleDataUtil.DEFAULT_USER.getAge()
                        ),
                        UserResponse.class
                );

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        assertUserFieldsEqual(SampleDataUtil.DEFAULT_USER, postResponse.getBody());

        // record id
        Long userId = postResponse.getBody().id();

        ResponseEntity<UserResponse> getResponse =
                restTemplate.getForEntity(baseUrl + "/" + userId, UserResponse.class);

        // get
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertUserFieldsEqual(SampleDataUtil.DEFAULT_USER, getResponse.getBody());

        // update
        ResponseEntity<Void> putResponse =
                restTemplate.exchange(
                        baseUrl,
                        HttpMethod.PUT,
                        new HttpEntity<>(
                                new UserUpdateRequest(
                                        userId,
                                        SampleDataUtil.NEW_USER.getEmail(),
                                        SampleDataUtil.NEW_USER.getName(),
                                        SampleDataUtil.NEW_USER.getAge()
                                )
                        ),
                        Void.class
                );
        assertEquals(HttpStatus.NO_CONTENT, putResponse.getStatusCode());

        // get again
        getResponse =
                restTemplate.getForEntity(baseUrl + "/" + userId, UserResponse.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertUserFieldsEqual(SampleDataUtil.NEW_USER, getResponse.getBody());

        // delete
        ResponseEntity<Void> deleteResponse =
                restTemplate.exchange(
                        baseUrl + "/" + userId,
                        HttpMethod.DELETE,
                        null,
                        Void.class
                );
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // try to get deleted user
        ResponseEntity<Void> emptyGetResponse =
                restTemplate.getForEntity(baseUrl + "/" + userId, Void.class);
        assertEquals(HttpStatus.NOT_FOUND, emptyGetResponse.getStatusCode());
    }
}
