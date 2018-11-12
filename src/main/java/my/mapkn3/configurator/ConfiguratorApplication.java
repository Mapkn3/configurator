package my.mapkn3.configurator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("my.mapkn3.configurator.repository")
@EntityScan("my.mapkn3.configurator.model")
@SpringBootApplication
public class ConfiguratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfiguratorApplication.class, args);
    }
}
