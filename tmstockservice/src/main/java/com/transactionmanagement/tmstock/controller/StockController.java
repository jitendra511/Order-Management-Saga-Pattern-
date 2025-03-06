package com.transactionmanagement.tmstock.controller;

import com.transactionmanagement.tmstock.entity.Stock;
import com.transactionmanagement.tmstock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getQuantity")
    public Long getQuantity(@RequestParam String item)
    {
        return stockService.getQuantity(item);
    }
}
