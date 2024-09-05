package com.app.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.app.loans.controller") })
@EnableJpaRepositories("com.app.loans.repository")
@EntityScan("com.app.loans.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "eBank Loans microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Mayur Badhe",
						email = "badhemayur01@gmail.com",
						url = "https://www.ebank.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.ebank.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EBank Loans microservice REST API Documentation",
				url = "https://www.ebank.com/swagger-ui.html"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}
}
