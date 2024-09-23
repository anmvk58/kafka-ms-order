package com.anmv.kafka.command.service;

import com.anmv.kafka.api.request.OrderRequest;
import com.anmv.kafka.broker.message.OrderMessage;
import com.anmv.kafka.command.action.OrderAction;
import com.anmv.kafka.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderAction action;

    public String saveOrder(OrderRequest request) {
        Order orderEntity = action.convertToOrder(request);

        action.saveToDatabase(orderEntity);

        orderEntity.getOrderItems().forEach(item -> {
            OrderMessage orderMessage = action.convertToOrderMessage(item);

            action.sendToKafka(orderMessage);
        });

        return orderEntity.getOrderNumber();
    }
}
