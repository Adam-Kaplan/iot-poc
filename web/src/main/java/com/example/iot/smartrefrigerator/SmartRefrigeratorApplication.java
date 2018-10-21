package com.example.iot.smartrefrigerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages= {"com.example.iot.shared"})
public class SmartRefrigeratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartRefrigeratorApplication.class, args);
	}
}
