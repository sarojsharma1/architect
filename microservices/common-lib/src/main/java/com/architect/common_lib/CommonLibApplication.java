package com.architect.common_lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class CommonLibApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonLibApplication.class, args);
    }

}
