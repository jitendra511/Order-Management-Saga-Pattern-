package com.transactionmanagement.tmpayment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long paymentId;
    private Long orderId;
    private Integer amount;
    private String paymentMode;
    private String status;
}
