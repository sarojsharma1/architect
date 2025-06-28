package com.architect.data_service.controller;

import com.architect.data_service.service.RabbitMQProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private final RabbitMQProducer rabbitMQProducer;

    public TestController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @GetMapping
    public String test() {
        rabbitMQProducer.send("Saroj");
        return "Data-service";
    }
}
