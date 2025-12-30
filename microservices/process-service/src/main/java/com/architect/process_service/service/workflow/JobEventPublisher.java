package com.architect.process_service.service.workflow;

import com.architect.common_lib.config.RabbitConfig;
import com.architect.process_service.service.workflow.dto.EventDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public JobEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(EventDto eventDto) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, eventDto);
    }
}
