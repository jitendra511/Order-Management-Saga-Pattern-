package com.transactionmanagement.tmdelivery.repository;

import com.transactionmanagement.tmdelivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
}
