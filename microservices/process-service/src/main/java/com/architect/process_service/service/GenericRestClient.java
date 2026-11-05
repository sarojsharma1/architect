package com.architect.process_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class GenericRestClient {
    public final WebClient.Builder webClientBuilder;

    public GenericRestClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public <T> Mono<T> get(String baseUrl,
                           String uri,
                           Map<String, String> pathVariables,
                           MultiValu
                           String requestParams,
                           Class<T> responseType) {
        WebClient webClient = this.webClientBuilder.baseUrl(baseUrl).build();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType);
    }
}
