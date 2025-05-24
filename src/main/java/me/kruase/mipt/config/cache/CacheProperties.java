package me.kruase.mipt.config.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {
    private int expireAfterSeconds = 600;
    private int initialCapacity = 10;
    private int maximumSize = 1000;
}
