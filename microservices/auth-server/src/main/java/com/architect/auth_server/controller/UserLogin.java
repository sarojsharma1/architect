package com.architect.auth_server.controller;

import com.architect.auth_server.util.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("login")
public class UserLogin {
    @Autowired
    private KeyGenerator keyGenerator;

    @PostMapping()
    public Mono<String> test() {
        return keyGenerator.getKey();
    }
}
