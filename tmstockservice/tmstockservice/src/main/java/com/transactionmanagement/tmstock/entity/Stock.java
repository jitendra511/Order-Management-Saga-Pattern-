package com.transactionmanagement.tmstock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Stock {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long itemId;
    private String itemName;
    private Long quantity;
}
