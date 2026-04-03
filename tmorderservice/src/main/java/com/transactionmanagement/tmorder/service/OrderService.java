package com.transactionmanagement.tmorder.service;

import com.transactionmanagement.tmorder.configuration.JwtUtil;
import com.transactionmanagement.tmorder.repository.OrderRepository;
import com.transactionmanagement.tmorder.dto.OrderEvent;
import com.transactionmanagement.tmorder.entity.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private KafkaTemplate<String,OrderEvent> kafkaTemplate;
    @Autowired
    private JwtUtil jwtUtil;
    public CustomerOrder createOrder(CustomerOrder customerOrder)
    {
        System.out.println("creating order");
        try
        {
            String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            Long userId = jwtUtil.getUserId(token);
            customerOrder.setUserId(userId);
            customerOrder.setStatus("Created");
            orderRepository.save(customerOrder);
            OrderEvent orderEvent=new OrderEvent();
            orderEvent.setType("Order Created");
            orderEvent.setToken(token);
            orderEvent.setCustomerOrder(customerOrder);
            kafkaTemplate.send("NEW-ORDER",orderEvent);
        }
        catch (Exception e)
        {
            System.out.println("creating order is failed");
            customerOrder.setStatus("Failed");
            orderRepository.save(customerOrder);
        }
        return customerOrder;
    }
    public List<CustomerOrder> getMyOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    public List<CustomerOrder> getAllOrder() {
        return orderRepository.findAll();
    }
}
