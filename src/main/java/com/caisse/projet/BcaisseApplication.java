package com.caisse.projet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAspectJAutoProxy
@EnableScheduling
//@Configuration
//@SpringBootApplication(scanBasePackages = "com.caisse.projet")
//@EnableWebMvc

@SpringBootApplication
public class BcaisseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BcaisseApplication.class, args);
	}

}
