package com.architect.process_service.service.job;

import com.architect.process_service.service.job.dto.EventDto;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer {
    private final JobHandler jobHandler;

    Consumer(JobHandler jobHandler) {
        this.jobHandler = jobHandler;
    }

    @RabbitListener(queues = "demoQueue", ackMode = "MANUAL")
    public void receive(EventDto eventDto, Message message, Channel channel) throws IOException {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            jobHandler.startAsyncTask(eventDto);
            //saveEvent(eventDto);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }
}
