package com.architect.common_lib.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE = "demoQueue";
    public static final String EXCHANGE = "demoExchange";
    public static final String ROUTING_KEY = "demoRoutingKey";
    public static final String DL_QUEUE = "deadLetterQueue";
    public static final String DL_EXCHANGE = "deadLetterExchange";
    public static final String DL_ROUTING_KEY = "deadLetterRoutingKey";

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE)
                .withArgument("x-overflow", "drop-head").withArgument("x-max-length", 1000)
                .withArgument("x-message-ttl", 2000)
                .withArgument("x-dead-letter-exchange", DL_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DL_ROUTING_KEY)
                .build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DL_QUEUE)
                .build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DL_EXCHANGE);
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DL_ROUTING_KEY);
    }
}