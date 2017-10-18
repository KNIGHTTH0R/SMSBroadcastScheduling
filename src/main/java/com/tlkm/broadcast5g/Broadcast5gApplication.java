package com.tlkm.broadcast5g;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Broadcast5gApplication {

	public static void main(String[] args) {
		SpringApplication.run(Broadcast5gApplication.class, args);
	}
}
