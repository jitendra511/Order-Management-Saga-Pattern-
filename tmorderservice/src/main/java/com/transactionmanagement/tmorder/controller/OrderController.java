package com.transactionmanagement.tmorder.controller;

import com.transactionmanagement.tmorder.configuration.JwtUtil;
import com.transactionmanagement.tmorder.service.OrderService;
import com.transactionmanagement.tmorder.entity.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    JwtUtil jwtUtil;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/createOrder")
    public CustomerOrder createOrder(@RequestBody CustomerOrder customerOrder)
    {
        return orderService.createOrder(customerOrder);
    }

    @GetMapping("/getAllOrder")
    public List<CustomerOrder> getAllOrder()
    {
        return orderService.getAllOrder();
    }

    @GetMapping("/getMyOrders")
    public List<CustomerOrder> getMyOrders(@RequestHeader("Authorization") String token)
    {
        String jwt=token.substring(7);
        Long userId=jwtUtil.getUserId(jwt);
        return orderService.getMyOrders(userId);
    }
}
