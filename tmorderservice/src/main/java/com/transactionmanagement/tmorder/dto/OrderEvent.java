package com.transactionmanagement.tmorder.dto;

import com.transactionmanagement.tmorder.entity.CustomerOrder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderEvent {
    private String type;
    private CustomerOrder customerOrder;
    private String token;
}
