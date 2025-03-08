package me.kruase.mipt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, CassandraAutoConfiguration.class})
@EnableAsync
public class MiptApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiptApplication.class, args);
    }
}
