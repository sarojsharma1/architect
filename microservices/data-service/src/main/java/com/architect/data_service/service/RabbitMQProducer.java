package com.architect.data_service.service;

import com.architect.common_lib.config.RabbitConfig;
import com.architect.data_service.event.EventDto;
import com.architect.data_service.event.JobStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {
        EventDto eventDto = EventDto.builder()
                .workflowId("1")
                .jobId("1")
                .status(JobStatus.INITIATED)
                .occurredAt(Instant.now())
                .attempt(0)
                .build();
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, eventDto);
    }
}
