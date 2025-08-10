package com.architect.process_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {
		"com.architect.process_service",
		"com.architect.common_lib"
})
@EnableDiscoveryClient
public class ProcessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessServiceApplication.class, args);
	}

}
