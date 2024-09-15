package com.anmv.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

//    This will create Topic if it not exists when run program
    @Bean
    NewTopic topicCommodityOrder() {
        return TopicBuilder.name("t-commodity-order")
                .partitions(2)
                .replicas(1)
                .build();
    }
}
