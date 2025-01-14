package com.transactionmanagement.tmstock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionmanagement.tmstock.dto.CustomerOrder;
import com.transactionmanagement.tmstock.dto.PaymentEvent;
import com.transactionmanagement.tmstock.dto.StockEvent;
import com.transactionmanagement.tmstock.entity.Stock;
import com.transactionmanagement.tmstock.repository.StockRepository;
import com.transactionmanagement.tmstock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addItem")
    public Stock addItem(@RequestBody Stock stock)
    {
        return stockService.addItem(stock);
    }

    @PreAuthorize("hasRole('ADMIN',USER)")
    @GetMapping("/getQuantity")
    public Long getQuantity(@RequestParam String item)
    {
        return stockService.getQuantity(item);
    }
}
