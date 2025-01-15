package com.transactionmanagement.tmpayment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEvent {
    private String type;
    private CustomerOrder customerOrder;
    private String token;
}
