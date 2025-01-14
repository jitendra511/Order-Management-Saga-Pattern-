package com.transactionmanagement.tmpayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TmpaymentserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmpaymentserviceApplication.class, args);
	}

}
