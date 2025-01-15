package com.transactionmanagement.tmpayment.kafkaListner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionmanagement.tmpayment.dto.CustomerOrder;
import com.transactionmanagement.tmpayment.dto.OrderEvent;
import com.transactionmanagement.tmpayment.dto.PaymentEvent;
import com.transactionmanagement.tmpayment.entity.Payment;
import com.transactionmanagement.tmpayment.feign.UserService;
import com.transactionmanagement.tmpayment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

public class CreatePayment {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private KafkaTemplate<String, PaymentEvent> paymentKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, OrderEvent> orderKafkaTemplate;
    @Autowired
    private UserService userService;

    @KafkaListener(topics = "NEW-ORDER",groupId = "ORDER-GROUP")
    public void createPayment(String event) throws Exception {
        System.out.println("creating the payment");
        OrderEvent orderEvent=new ObjectMapper().readValue(event,OrderEvent.class);
        String token = orderEvent.getToken();
        System.out.println("token "+token);
        CustomerOrder customerOrder=orderEvent.getCustomerOrder();
        Integer totalPayment= (int)(customerOrder.getQuantity()*customerOrder.getPrice());
        Payment payment=new Payment();
        try
        {
            Integer currentBalance=userService.getBalance("Bearer " +token);
            Integer remainingBalance = null;
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
            System.out.println("remaining balance "+remainingBalance);
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
            oEvent.setType("REVERSED-ORDER");
            oEvent.setCustomerOrder(customerOrder);
            orderKafkaTemplate.send("REVERSED-ORDER",oEvent);
        }
    }
}
