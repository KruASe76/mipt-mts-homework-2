package me.kruase.mipt.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {
    private final CacheProperties cacheProperties;

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .initialCapacity(cacheProperties.getInitialCapacity())
                .maximumSize(cacheProperties.getMaximumSize())
                .expireAfterAccess(cacheProperties.getExpireAfterSeconds(), TimeUnit.SECONDS);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("mainCache");
        cacheManager.setCaffeine(caffeine);
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }
}
