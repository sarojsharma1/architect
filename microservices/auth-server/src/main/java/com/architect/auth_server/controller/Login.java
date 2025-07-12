package com.architect.auth_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("login")
public class Login {
    @GetMapping()
    public Mono<Void> test() {
        System.out.println("Login");
        return Mono.empty();
    }
}
