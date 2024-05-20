package com.example.microservice_small_square;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceSmallSquareApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceSmallSquareApplication.class, args);
	}

}
