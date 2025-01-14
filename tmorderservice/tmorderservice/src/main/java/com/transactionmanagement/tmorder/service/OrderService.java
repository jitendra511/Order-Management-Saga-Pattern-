package com.transactionmanagement.tmorder.service;

import com.transactionmanagement.tmorder.repository.OrderRepository;
import com.transactionmanagement.tmorder.dto.OrderEvent;
import com.transactionmanagement.tmorder.entity.CustomerOrder;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private KafkaTemplate<String,OrderEvent> kafkaTemplate;
    public CustomerOrder createOrder(CustomerOrder customerOrder)
    {
        System.out.println("creating order");
        try
        {
            customerOrder.setStatus("Created");
            orderRepository.save(customerOrder);
            String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            System.out.println("token "+token);
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
}
