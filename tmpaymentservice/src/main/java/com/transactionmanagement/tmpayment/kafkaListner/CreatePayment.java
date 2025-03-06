package com.transactionmanagement.tmpayment.kafkaListner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionmanagement.tmpayment.configuration.JwtUtil;
import com.transactionmanagement.tmpayment.dto.CustomerOrder;
import com.transactionmanagement.tmpayment.dto.OrderEvent;
import com.transactionmanagement.tmpayment.dto.PaymentEvent;
import com.transactionmanagement.tmpayment.entity.Payment;
import com.transactionmanagement.tmpayment.feign.UserService;
import com.transactionmanagement.tmpayment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Component
public class CreatePayment {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private KafkaTemplate<String, PaymentEvent> paymentKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, OrderEvent> orderKafkaTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @KafkaListener(topics = "NEW-ORDER",groupId = "ORDER-GROUP")
    public void createPayment(String event) throws Exception {
        System.out.println("creating the payment");
        OrderEvent orderEvent=new ObjectMapper().readValue(event,OrderEvent.class);
        String token = orderEvent.getToken();
        CustomerOrder customerOrder=orderEvent.getCustomerOrder();
        Integer totalPayment= (int)(customerOrder.getQuantity()*customerOrder.getPrice());
        Payment payment=new Payment();
        try
        {
            if (token == null || !jwtUtil.validateToken(token)) {
                System.out.println("Invalid JWT Token. Rejecting stock update.");
                return;
            }
            Integer currentBalance=userService.getBalance("Bearer " +token);
            Integer remainingBalance = 0;
            if(currentBalance>=totalPayment)
            {
                remainingBalance=currentBalance-totalPayment;
                userService.updateBalance("Bearer "+token,remainingBalance);
                payment.setPaymentMode(customerOrder.getPaymentMode());
                payment.setStatus("Success");
            }
            else {
                throw new Exception("Balance is not enough");
            }
            payment.setOrderId(customerOrder.getOrderId());
            payment.setAmount(totalPayment);
            paymentRepository.save(payment);
            PaymentEvent paymentEvent=new PaymentEvent();
            paymentEvent.setType("Payment created");
            paymentEvent.setCustomerOrder(customerOrder);
            paymentEvent.setToken(token);
            paymentKafkaTemplate.send("NEW-PAYMENT",paymentEvent);
        }
        catch (Exception e)
        {
            System.out.println("payment is failed"+e.getMessage());
            payment.setOrderId(customerOrder.getOrderId());
            payment.setStatus("Failed");
            paymentRepository.save(payment);
            OrderEvent oEvent=new OrderEvent();
            oEvent.setToken(token);
            oEvent.setType("REVERSED-ORDER");
            oEvent.setCustomerOrder(customerOrder);
            orderKafkaTemplate.send("REVERSED-ORDER",oEvent);
        }
    }
}
