package com.architect.data_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.concurrent.CompletableFuture;

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
    public Flux<String> callProcessService() {
        return webClient.get().uri("/process/test/web")
                .exchangeToFlux(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToFlux(String.class);
                    } else {
                        return Flux.error(new RuntimeException("Downstream error"));
                    }
                });
    }

    public Flux<String> fallbackHandler(Throwable t) {
        System.out.println("Unable to connect to service " + t.getMessage());
        return Flux.just("Fallback response");
    }

    @Async
    public void asyncOp() {
        System.out.println("Hello");
    }

    public void customAsyncOp() {
        CompletableFuture<String> res = CompletableFuture.supplyAsync(() -> {
            return "success";
        }).exceptionally(ex -> {
            return "error";
        });
    }
}
