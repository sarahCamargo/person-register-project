package com.crud.person.project.csv.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String CSV_QUEUE = "csvQueue";

    @Bean
    public Queue csvQueue() {
        return new Queue(CSV_QUEUE, true);
    }

}
