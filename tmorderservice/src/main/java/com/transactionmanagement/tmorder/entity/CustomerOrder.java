package com.transactionmanagement.tmorder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long orderId;
    private Long userId;
    private String itemName;
    private int quantity;
    private int price;
    private String paymentMode;
    private String status;
    private String address;
}
