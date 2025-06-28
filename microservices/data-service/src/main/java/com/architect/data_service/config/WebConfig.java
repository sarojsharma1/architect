package com.architect.data_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
