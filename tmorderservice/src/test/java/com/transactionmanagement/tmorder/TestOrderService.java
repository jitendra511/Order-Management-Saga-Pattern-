package com.transactionmanagement.tmorder;

import com.transactionmanagement.tmorder.configuration.JwtUtil;
import com.transactionmanagement.tmorder.dto.OrderEvent;
import com.transactionmanagement.tmorder.entity.CustomerOrder;
import com.transactionmanagement.tmorder.repository.OrderRepository;
import com.transactionmanagement.tmorder.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestOrderService {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @BeforeEach
    void setup()
    {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    public void TestCreateOrder()
    {
        CustomerOrder customerOrder=new CustomerOrder(1L,1L,"Apple",10,50,"CREDIT_CARD","CREATE","Nanakmatta");
        String token = "mocked-jwt-token";

        when(authentication.getCredentials()).thenReturn(token);
        when(jwtUtil.getUserId(token)).thenReturn(customerOrder.getUserId());
        when(orderRepository.save(ArgumentMatchers.<CustomerOrder>any())).thenAnswer(invocation -> invocation.getArgument(0));

        CustomerOrder result=orderService.createOrder(customerOrder);
        Assertions.assertEquals(customerOrder,result);
        Assertions.assertEquals("Created",result.getStatus());
    }
}
