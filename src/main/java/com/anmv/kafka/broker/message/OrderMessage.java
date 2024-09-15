package com.anmv.kafka.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {

    private String orderLocation;

    private String orderNumber;

    private String creditCardNumber;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetAndMetadata orderDateTime;

    private String itemName;

    private int price;

    private int quantity;

}
