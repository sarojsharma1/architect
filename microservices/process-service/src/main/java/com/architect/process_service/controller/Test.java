package com.architect.process_service.controller;

import com.architect.process_service.service.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/test")
public class Test {
    @Autowired
    GrpcClient grpcClient;

    @GetMapping
    public String test() {
        grpcClient.callService();
        return "Process service";
    }

    @GetMapping(value = "web", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getData() {
        return Flux.just("apple", "banana", "cherry")
                .delayElements(Duration.ofSeconds(10));
    }
}
