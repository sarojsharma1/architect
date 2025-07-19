package com.architect.common_lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class CommonLibApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonLibApplication.class, args);
    }

}
