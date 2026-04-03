package com.transactionmanagement.tmorder;

import com.transactionmanagement.tmorder.controller.OrderController;
import com.transactionmanagement.tmorder.entity.CustomerOrder;
import com.transactionmanagement.tmorder.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestOrderController {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void testCreateOrder()
    {
        CustomerOrder customerOrder=new CustomerOrder(1L,1L,"Apple",10,50,"CREDIT_CARD","CREATE","Nanakmatta");
        when(orderService.createOrder(customerOrder)).thenReturn(customerOrder);
        CustomerOrder result=orderController.createOrder(customerOrder);
        Assertions.assertEquals(customerOrder,result);
    }

    @Test
    public void testGetAllOrder()
    {
        List<CustomerOrder> orders=new ArrayList<>();
        orders.add(new CustomerOrder(1L,1L,"Apple",10,50,"CREDIT_CARD","CREATE","Nanakmatta"));
        orders.add(new CustomerOrder(2L,2L,"Banana",5,40,"CASH","CREATE","Delhi"));
        when(orderService.getAllOrder()).thenReturn(orders);

        List<CustomerOrder> result=orderController.getAllOrder();
        Assertions.assertEquals(orders,result);
        Assertions.assertEquals(2,result.size());
    }

}
