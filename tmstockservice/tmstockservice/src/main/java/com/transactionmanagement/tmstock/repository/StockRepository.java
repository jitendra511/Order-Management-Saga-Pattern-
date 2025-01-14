package com.transactionmanagement.tmstock.repository;

import com.transactionmanagement.tmstock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    public Iterable<Stock> findByItemName(String item);
    @Query("SELECT i.quantity FROM Stock i WHERE i.itemName = :item")
    public Long findQuantityByItemName(String item);
}
