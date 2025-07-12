package com.architect.auth_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("register")
public class UserRegister {
    @GetMapping()
    public Mono<Void> test() {
        System.out.println("Register");
        return Mono.empty();
    }
}
