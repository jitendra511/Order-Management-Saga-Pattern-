package com.transactionmanagement.tmstock.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentEvent {
    private String type;
    private CustomerOrder customerOrder;
    private String token;
}
