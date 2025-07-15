package com.architect.auth_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class UserRegister {
    @GetMapping()
    public void test() {
        System.out.println("Register");
    }
}
