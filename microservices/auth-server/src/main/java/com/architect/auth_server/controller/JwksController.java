package com.architect.auth_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(".well-known")
public class JwksController {
    @GetMapping("/jwks.json")
    public void test() {
    }
}
