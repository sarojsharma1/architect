package com.architect.process_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "demoQueue")
    public void receive(String message) {
        System.out.println("Test");
    }
}
