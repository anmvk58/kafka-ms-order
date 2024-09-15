package com.anmv.kafka.broker.producer;

import com.anmv.kafka.broker.message.OrderMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void sendOrder(OrderMessage orderMessage) {
        kafkaTemplate.send("t-commodity-order", orderMessage.getOrderNumber(), orderMessage);
    }
}
