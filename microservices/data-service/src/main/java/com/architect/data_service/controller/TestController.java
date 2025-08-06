package com.architect.data_service.controller;

import com.architect.data_service.service.RabbitMQProducer;
import com.architect.data_service.service.RestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private final RabbitMQProducer rabbitMQProducer;
    private final RestClient restClient;

    public TestController(RabbitMQProducer rabbitMQProducer, RestClient webClient) {
        this.rabbitMQProducer = rabbitMQProducer;
        this.restClient = webClient;
    }

    @GetMapping
    public String test() {
        rabbitMQProducer.send("Message");
        restClient.callProcessService().subscribe(
                data -> {
                    System.out.println("Data fetched: " + data);
                },
                error -> {
                    System.out.println("Error occurred" + error.getMessage());
                },
                () -> {
                    System.out.println("Completed");
                });
        return "Data-service";
    }
}
