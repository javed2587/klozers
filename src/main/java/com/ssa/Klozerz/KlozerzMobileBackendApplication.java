package com.ssa.Klozerz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Klozerz API's", version = "1.0", description = "Api Documentation"))
public class KlozerzMobileBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(KlozerzMobileBackendApplication.class, args);
	}

}
