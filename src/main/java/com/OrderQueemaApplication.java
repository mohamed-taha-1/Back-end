package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.security.AppProperties;

@SpringBootApplication
public class OrderQueemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderQueemaApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationCustomContext springApplicationCustomContext( ) {
		return new SpringApplicationCustomContext();
	}
	
	@Bean(name="AppProperties")
	public AppProperties getAppProperties()
	{
		return new AppProperties();
	}
}
