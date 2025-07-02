package com.architect.data_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RestClient {
    private final WebClient webClient;
    public RestClient(@Value("${process.service.baseUrl}") String processServiceBaseURL,
                      WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(processServiceBaseURL)
                .build();
    }

    public void callProcessService() {
        webClient.get().uri("/process/test/web")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
