package com.transactionmanagement.tmstock.kafkaListner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionmanagement.tmstock.dto.CustomerOrder;
import com.transactionmanagement.tmstock.dto.PaymentEvent;
import com.transactionmanagement.tmstock.dto.StockEvent;
import com.transactionmanagement.tmstock.entity.Stock;
import com.transactionmanagement.tmstock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockListner {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private KafkaTemplate<String, StockEvent> stockKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, PaymentEvent> paymentKafkaTemplate;

    @KafkaListener(topics = "NEW-PAYMENT",groupId = "PAYMENT-GROUP")
    public void updateStock(String event) throws Exception {
        System.out.println("updating stock after creating order");
        PaymentEvent paymentEvent=new ObjectMapper().readValue(event, PaymentEvent.class);
        CustomerOrder customerOrder=paymentEvent.getCustomerOrder();
        Stock stock=new Stock();
        try
        {
            Iterable<Stock> stocks=stockRepository.findByItemName(customerOrder.getItemName());
            boolean itemExist=stocks.iterator().hasNext();
            if(!itemExist)
            {
                System.out.println("item is not exist so revert the order");
                throw new Exception("Stock is unavailable");
            }
            if (stockRepository.findQuantityByItemName(customerOrder.getItemName()) < customerOrder.getQuantity()) {
                System.out.println("Insufficient stock, reverting the order.");
                throw new Exception("Insufficient stock for item: " + customerOrder.getItemName());
            }
            stocks.forEach(i->{
                if(stockRepository.findQuantityByItemName(i.getItemName())>=customerOrder.getQuantity())
                {
                    i.setQuantity(i.getQuantity()-customerOrder.getQuantity());
                    stockRepository.save(i);
                }
            });
            StockEvent stockEvent=new StockEvent();
            stockEvent.setType("STOCK-UPDATED");
            stockEvent.setCustomerOrder(customerOrder);
            stockKafkaTemplate.send("NEW-STOCK",stockEvent);
        }
        catch (Exception e)
        {
            System.out.println("updating stock is failed so reverting the order");
            PaymentEvent pEvent=new PaymentEvent();
            pEvent.setType("PAYMENT-REVERSED");
            pEvent.setCustomerOrder(customerOrder);
            paymentKafkaTemplate.send("REVERSE-PAYMENT",pEvent);
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
