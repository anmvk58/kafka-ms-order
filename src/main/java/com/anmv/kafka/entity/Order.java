package com.anmv.kafka.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private int orderId;

    @Column
    private String orderNumber;

    @Column
    private String orderLocation;

    @Column
    private OffsetDateTime orderDateTime;

    @Column
    private String creditCardNumber;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
