package com.anmv.kafka.broker.producer;

import com.anmv.kafka.broker.message.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void sendOrder(OrderMessage orderMessage) {
        kafkaTemplate.send("t-commodity-order", orderMessage.getOrderNumber(), orderMessage).whenComplete(
                (recordMetadata, ex) -> {
                    if (ex == null) {
                        LOG.info("Order {} sent successfully", orderMessage.getOrderNumber());
                    } else {
                        LOG.error("Order {} failed to send", orderMessage.getOrderNumber(), ex);
                    }
                }
        );

        LOG.info("Dummy message for order {}, item {}", orderMessage.getOrderNumber(), orderMessage.getItemName());
    }
}
