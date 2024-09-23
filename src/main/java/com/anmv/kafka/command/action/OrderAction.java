package com.anmv.kafka.command.action;

import com.anmv.kafka.api.request.OrderRequest;
import com.anmv.kafka.broker.message.OrderMessage;
import com.anmv.kafka.broker.producer.OrderProducer;
import com.anmv.kafka.entity.Order;
import com.anmv.kafka.entity.OrderItem;
import com.anmv.kafka.repository.OrderItemRepository;
import com.anmv.kafka.repository.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;


@Component
public class OrderAction {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order convertToOrder(OrderRequest request) {
        Order order = new Order();

        order.setOrderLocation(request.getOrderLocation());
        order.setCreditCardNumber(request.getCreditCardNumber());
        order.setOrderDateTime(OffsetDateTime.now());
        order.setOrderNumber(RandomStringUtils.randomAlphabetic(8).toUpperCase());

        var orderItems = request.getItems().stream().map(
                item -> {
                    OrderItem orderItem = new OrderItem();

                    orderItem.setItemName(item.getItemName());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setOrder(order);
                    return orderItem;
                }
        ).toList();

        order.setOrderItems(orderItems);
        return order;
    }

    public void saveToDatabase(Order orderEntity) {
        orderRepository.save(orderEntity);
//        this method replace by idea suggest
//        orderEntity.getOrderItems().forEach(orderItemRepository::save);
        orderItemRepository.saveAll(orderEntity.getOrderItems());
    }

    public OrderMessage convertToOrderMessage(OrderItem item) {
        OrderMessage orderMessage = new OrderMessage();

        orderMessage.setItemName(item.getItemName());
        orderMessage.setPrice(item.getPrice());
        orderMessage.setQuantity(item.getQuantity());
        orderMessage.setOrderNumber(item.getOrder().getOrderNumber());
        orderMessage.setOrderDateTime(item.getOrder().getOrderDateTime());
        orderMessage.setCreditCardNumber(item.getOrder().getCreditCardNumber());
        orderMessage.setOrderLocation(item.getOrder().getOrderLocation());

        return orderMessage;
    }

    public void sendToKafka(OrderMessage orderMessage) {
        orderProducer.sendOrder(orderMessage);
    }
}
