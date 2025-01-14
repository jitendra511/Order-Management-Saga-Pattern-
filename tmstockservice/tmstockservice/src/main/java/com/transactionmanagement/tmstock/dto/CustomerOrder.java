package com.transactionmanagement.tmstock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerOrder {
    private Long orderId;
    private String itemName;
    private int quantity;
    private Long price;
    private String paymentMode;
    private String status;
    private String address;
}
