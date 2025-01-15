package com.transactionmanagement.tmorder.controller;

import com.transactionmanagement.tmorder.service.OrderService;
import com.transactionmanagement.tmorder.entity.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/createOrder")
    public CustomerOrder createOrder(@RequestBody CustomerOrder customerOrder)
    {
        return orderService.createOrder(customerOrder);
    }
}
