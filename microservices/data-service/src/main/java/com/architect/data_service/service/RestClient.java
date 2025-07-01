package com.architect.data_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RestClient {
    private final WebClient webClient;

    public RestClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://process-service")
                .build();
    }

    public void callProcessService() {
        webClient.get().uri("/process/test/web")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
