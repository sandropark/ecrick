package sandro.elib.elib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ElibApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElibApplication.class, args);
    }

}
