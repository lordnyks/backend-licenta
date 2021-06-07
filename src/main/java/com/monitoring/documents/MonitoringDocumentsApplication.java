package com.monitoring.documents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MonitoringDocumentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringDocumentsApplication.class, args);
	}

}

