package com.transactionmanagement.tmstock.kafkaListner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionmanagement.tmstock.dto.CustomerOrder;
import com.transactionmanagement.tmstock.dto.DeliveryEvent;
import com.transactionmanagement.tmstock.dto.PaymentEvent;
import com.transactionmanagement.tmstock.dto.StockEvent;
import com.transactionmanagement.tmstock.entity.Stock;
import com.transactionmanagement.tmstock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockReverse {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private KafkaTemplate<String,PaymentEvent> paymentKafkaTemplate;

    @KafkaListener(topics = "STOCK-REVERSED",groupId = "STOCK-GROUP")
    public void stockReversed(String event)
    {
        System.out.println("update the stock after failed delivery");
        try
        {
            StockEvent stockEvent=new ObjectMapper().readValue(event,StockEvent.class);
            CustomerOrder customerOrder=stockEvent.getCustomerOrder();
            Iterable<Stock> stocks=stockRepository.findByItemName(customerOrder.getItemName());
            stocks.forEach(i->{
                i.setQuantity(i.getQuantity()+stockEvent.getCustomerOrder().getQuantity());
                stockRepository.save(i);
            });
            PaymentEvent paymentEvent=new PaymentEvent();
            paymentEvent.setType("REVERSED-PAYMENT");
            paymentEvent.setCustomerOrder(stockEvent.getCustomerOrder());
            paymentKafkaTemplate.send("REVERSE-PAYMENT",paymentEvent);
        }
        catch (Exception e)
        {
            System.out.println("Exception occured dusring reverse the stock" +e);
        }
    }
}
