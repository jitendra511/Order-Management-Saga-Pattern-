package com.transactionmanagement.tmdelivery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockEvent {
    private String type;
    private CustomerOrder customerOrder;
}
