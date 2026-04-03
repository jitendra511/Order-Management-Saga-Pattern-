package com.transactionmanagement.tmpayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionmanagement.tmpayment.configuration.JwtUtil;
import com.transactionmanagement.tmpayment.dto.CustomerOrder;
import com.transactionmanagement.tmpayment.dto.OrderEvent;
import com.transactionmanagement.tmpayment.dto.PaymentEvent;
import com.transactionmanagement.tmpayment.entity.Payment;
import com.transactionmanagement.tmpayment.feign.UserService;
import com.transactionmanagement.tmpayment.kafkaListner.CreatePayment;
import com.transactionmanagement.tmpayment.repository.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestPaymentService {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private KafkaTemplate<String, PaymentEvent> paymentKafkaTemplate;

    @Mock
    private KafkaTemplate<String, OrderEvent> orderKafkaTemplate;

    @InjectMocks
    private CreatePayment createPayment;

    @Mock
    private UserService userService;
    @Mock
    private JwtUtil jwtUtil;

    private OrderEvent orderEvent;

    private ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<Payment> paymentCaptor;

    @Test
    public void testPayment() throws Exception {
        objectMapper=new ObjectMapper();
        CustomerOrder customerOrder=new CustomerOrder(1L,"Apple",10,50L,"CREDIT_CARD","CREATE","Nanakmatta");
        orderEvent=new OrderEvent();
        orderEvent.setToken("valid_token");
        orderEvent.setType("Order Created");
        orderEvent.setCustomerOrder(customerOrder);

        String event = objectMapper.writeValueAsString(orderEvent);
        when(jwtUtil.validateToken("valid_token")).thenReturn(true);
        when(userService.getBalance("Bearer valid_token")).thenReturn(500);
        when(paymentRepository.save(ArgumentMatchers.<Payment>any())).thenReturn(new Payment());

        createPayment.createPayment(event);

        verify(paymentRepository).save(paymentCaptor.capture());
        Payment payment=paymentCaptor.getValue();
        Assertions.assertEquals("Success",payment.getStatus());
    }

}
