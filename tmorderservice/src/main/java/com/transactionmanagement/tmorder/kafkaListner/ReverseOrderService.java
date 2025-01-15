package com.transactionmanagement.tmorder.kafkaListner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionmanagement.tmorder.dto.OrderEvent;
import com.transactionmanagement.tmorder.repository.OrderRepository;
import com.transactionmanagement.tmorder.entity.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReverseOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "REVERSED-ORDER",groupId = "ORDER-GROUP")
    public void reverseOrder(String event)
    {
        System.out.println("reverse order");
        try
        {
            OrderEvent orderEvent=new ObjectMapper().readValue(event,OrderEvent.class);
            Optional<CustomerOrder> customerOrder=orderRepository.findById(orderEvent.getCustomerOrder().getOrderId());
            customerOrder.ifPresent(co->{
                co.setStatus("Failed");
                orderRepository.save(co);
            });
        }
        catch (Exception e)
        {
            System.out.println("Exception occured during revert the order"+e.getMessage());
        }
    }
}
