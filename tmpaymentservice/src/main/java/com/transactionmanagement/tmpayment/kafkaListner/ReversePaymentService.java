package com.transactionmanagement.tmpayment.kafkaListner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionmanagement.tmpayment.dto.CustomerOrder;
import com.transactionmanagement.tmpayment.dto.OrderEvent;
import com.transactionmanagement.tmpayment.entity.Payment;
import com.transactionmanagement.tmpayment.feign.UserService;
import com.transactionmanagement.tmpayment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ReversePaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private KafkaTemplate<String,OrderEvent> kafkaTemplate;
    @Autowired
    private UserService userService;

    @KafkaListener(topics = "REVERSE-PAYMENT",groupId = "PAYMENT-GROUP")
    public void reversePayment(String event) throws JsonProcessingException {
        System.out.println("reversing the payment after revert the stock");
        OrderEvent orderEvent = new ObjectMapper().readValue(event, OrderEvent.class);
        String token = orderEvent.getToken();
        System.out.println("token "+token);
        CustomerOrder customerOrder=orderEvent.getCustomerOrder();
        Integer totalPayment= (int)(customerOrder.getQuantity()*customerOrder.getPrice());
        System.out.println("total payment "+totalPayment);
        try
        {
            Optional<Payment> payment =paymentRepository.findById(customerOrder.getOrderId());
            payment.ifPresent(p->{
                System.out.println(p.getOrderId());
                Integer updatedBalance = userService.getBalance("Bearer " + token) + totalPayment;
                userService.updateBalance("Bearer " + token, updatedBalance);

                p.setStatus("Failed");
                paymentRepository.save(p);
            });
            orderEvent.setType("ORDER-REVERSED");
            orderEvent.setCustomerOrder(customerOrder);
            orderEvent.setToken(token);
            kafkaTemplate.send("REVERSED-ORDER",orderEvent);
        }catch (Exception e)
        {
            System.out.println("Exception occured during revert the payment"+e.getMessage());
        }
    }
}
