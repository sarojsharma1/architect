package com.architect.process_service.service.job;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class Consumer {
    @Autowired
    @Qualifier("taskExecutor")
    private Executor executor;

    @Autowired
    private RunJob runJob;

    @RabbitListener(queues = "demoQueue", ackMode = "MANUAL")
    public void receive(Message message, Channel channel) throws IOException {
        try {
            String body = new String(message.getBody());
            triggerJob(body);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    public void triggerJob(String event) {
        CompletableFuture.runAsync(() -> {
            String jobName = extractJobName(event);
            System.out.println(jobName);
            runJob.dispatchJob(jobName);
        }, executor);
    }

    public String extractJobName(String event) {
        //check idempotent
        //event process
        return event;
    }

    public void checkIdempotent() {

    }
}
