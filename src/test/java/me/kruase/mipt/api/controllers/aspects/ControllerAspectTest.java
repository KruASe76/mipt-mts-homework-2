package me.kruase.mipt.api.controllers.aspects;

import me.kruase.mipt.api.controllers.UserController;
import me.kruase.mipt.config.TestcontainersConfig;
import me.kruase.mipt.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.AopTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class ControllerAspectTest extends TestcontainersConfig {
    @Autowired
    private UserController userControllerProxy;

    @MockitoBean
    private UserService userService;

    @Test
    public void testCounter() {
        doNothing().when(userService).delete(anyLong());

        int initialCount = ControllerAspect.getEventCount();

        // regular call: both increment and first service call
        userControllerProxy.deleteUser(1L);

        assertEquals(initialCount + 2, ControllerAspect.getEventCount());
        verify(userService, times(1)).delete(1L);

        // raw call: no increment and second service call
        UserController rawController = AopTestUtils.getTargetObject(userControllerProxy);

        rawController.deleteUser(1L);

        assertEquals(initialCount + 2, ControllerAspect.getEventCount());
        verify(userService, times(2)).delete(1L);
    }
}
