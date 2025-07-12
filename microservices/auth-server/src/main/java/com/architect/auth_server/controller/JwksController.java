package com.architect.auth_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(".well-known")
public class JwksController {
    @GetMapping("/jwks.json")
    public Mono<Void> test() {
        return Mono.empty();
    }
}
