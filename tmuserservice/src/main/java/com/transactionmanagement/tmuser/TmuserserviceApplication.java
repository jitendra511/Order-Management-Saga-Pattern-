package com.transactionmanagement.tmuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TmuserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmuserserviceApplication.class, args);
	}

}
