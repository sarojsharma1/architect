package com.architect.process_service.controller;

import com.architect.process_service.service.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("web")
    public void webTest() {
        System.out.println("Congrats");
    }
}
