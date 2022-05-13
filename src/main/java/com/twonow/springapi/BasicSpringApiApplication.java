package com.twonow.springapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BasicSpringApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicSpringApiApplication.class, args);
	}

}
