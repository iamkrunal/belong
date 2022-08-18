package com.belong.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Phone Activation API", version = "1.0",
		description = "BELONG Phone number activation service. " +
				"This allows you certain services." +
				"You can retrieve all phone numbers in the database. " +
				"You can retrieve all phone numbers by a customer id" +
				"You can activate a single phone number"))
public class BelongApplication {

	public static void main(String[] args) {
		SpringApplication.run(BelongApplication.class, args);
	}

}
