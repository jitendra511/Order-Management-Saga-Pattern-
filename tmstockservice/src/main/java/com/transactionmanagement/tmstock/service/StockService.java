package com.transactionmanagement.tmstock.service;

import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.transactionmanagement.tmstock.entity.Stock;
import com.transactionmanagement.tmstock.repository.StockRepository;
import org.apache.kafka.clients.consumer.StickyAssignor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    public Stock addItem(Stock stock)
    {
        Iterable<Stock> item=stockRepository.findByItemName(stock.getItemName());
        if(item.iterator().hasNext())
        {
            item.forEach(i->{
                i.setQuantity(i.getQuantity()+ stock.getQuantity());
                stockRepository.save(i);
            });
        }
        else
        {
            Stock s=new Stock();
            s.setItemName(stock.getItemName());
            s.setQuantity(stock.getQuantity());
            stockRepository.save(s);
        }
        return stock;
    }

    public Long getQuantity(String item)
    {
        return stockRepository.findQuantityByItemName(item);
    }
}
