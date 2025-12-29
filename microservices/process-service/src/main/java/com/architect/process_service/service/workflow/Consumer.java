package com.architect.process_service.service.workflow;

import com.architect.process_service.service.workflow.dto.EventDto;
import com.architect.process_service.service.workflow.job.JobHandlerService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer {
    private final JobHandlerService jobHandlerService;

    Consumer(JobHandlerService jobHandlerService) {
        this.jobHandlerService = jobHandlerService;
    }

    @RabbitListener(queues = "demoQueue", ackMode = "MANUAL")
    public void receive(EventDto eventDto, Message message, Channel channel) throws IOException {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            jobHandlerService.dispatchJob(eventDto);
            // Persist the event before marking the job as completed
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }
}
