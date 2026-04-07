package com.periodTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeriodTrackerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeriodTrackerBackendApplication.class, args);
		System.out.println("Application started");
	}

}
