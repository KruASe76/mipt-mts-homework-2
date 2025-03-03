package me.kruase.mipt.actuator.metrics;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@Endpoint(id = "random-uuid")
public class RandomUuidActuatorEndpoint {
    @ReadOperation
    public Map<String, Object> randomUuid() {
        return Map.of("uuid", UUID.randomUUID());
    }
}
