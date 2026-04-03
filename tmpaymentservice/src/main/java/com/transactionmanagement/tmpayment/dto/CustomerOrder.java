package com.transactionmanagement.tmpayment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder {

    private Long orderId;
    private String itemName;
    private int quantity;
    private Long price;
    private String paymentMode;
    private String status;
    private String address;
    private Long userId;
}
