package com.architect.process_service.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQConsumer {
    @RabbitListener(queues = "demoQueue", ackMode = "MANUAL")
    public void receive(Message message, Channel channel) throws IOException {
        try {
            String body = new String(message.getBody());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println(body);

        } catch (IOException e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
