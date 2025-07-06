package com.architect.data_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
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

    @CircuitBreaker(name = "sampleService", fallbackMethod = "fallbackHandler")
    @Retry(name = "sampleService", fallbackMethod = "fallbackHandler")
    public void callProcessService() {
        webClient.get().uri("/process/test/web")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public void fallbackHandler(Throwable t) {
        System.out.println("Unable to connect to service " + t.getMessage());
    }
}
