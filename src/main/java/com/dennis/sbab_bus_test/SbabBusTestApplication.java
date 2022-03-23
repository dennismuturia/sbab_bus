package com.dennis.sbab_bus_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SbabBusTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbabBusTestApplication.class, args);
	}

}
