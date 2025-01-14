package com.transactionmanagement.tmdelivery.kafkalistner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionmanagement.tmdelivery.ExceptionHandler.AddressNotFound;
import com.transactionmanagement.tmdelivery.dto.CustomerOrder;
import com.transactionmanagement.tmdelivery.dto.DeliveryEvent;
import com.transactionmanagement.tmdelivery.dto.StockEvent;
import com.transactionmanagement.tmdelivery.entity.Delivery;
import com.transactionmanagement.tmdelivery.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeliveryKafkaListner {
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private KafkaTemplate<String,DeliveryEvent> deliveryKafkaTemplate;

    @KafkaListener(topics = "NEW-STOCK",groupId = "STOCK-GROUP")
    public void deliverOrder(String event) throws Exception
    {
        System.out.println("Inside ship order for order "+event);
        System.out.println("Delivery will reach to you in 3-4 bussiness days...");
        StockEvent stockEvent=new ObjectMapper().readValue(event, StockEvent.class);
        CustomerOrder customerOrder=stockEvent.getCustomerOrder();
        System.out.println(customerOrder.getAddress());
        Delivery delivery=new Delivery();
        try
        {
            if(customerOrder.getAddress()==null)
            {
                throw new AddressNotFound("Address not found");
            }
            delivery.setOrderId(customerOrder.getOrderId());
            delivery.setAddress(customerOrder.getAddress());
            delivery.setStatus("success");
            deliveryRepository.save(delivery);
            customerOrder.setStatus("Success");
        }
        catch (Exception e)
        {
            System.out.println("delivery is failed so revert to the stock");
            delivery.setId(customerOrder.getOrderId());
            delivery.setStatus("Failed");
            deliveryRepository.save(delivery);

            DeliveryEvent reverseDelivery=new DeliveryEvent();
            reverseDelivery.setType("Reverse Delivery");
            reverseDelivery.setCustomerOrder(customerOrder);
            deliveryKafkaTemplate.send("STOCK-REVERSED",reverseDelivery);
        }
    }
}
