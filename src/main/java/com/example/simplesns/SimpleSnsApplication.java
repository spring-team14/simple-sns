package com.example.simplesns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SimpleSnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSnsApplication.class, args);
    }

}
