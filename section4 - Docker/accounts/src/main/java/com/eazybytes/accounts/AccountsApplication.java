package com.eazybytes.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScan({@ComponentScan("com.eazybytes.accounts.controller")})
@EnableJpaRepositories("com.easybytes.accounts.repository")
@EntityScan("com.easybytes.accounts.model")
*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info=@Info(
                title = "Accounts MS" ,
                description = "Rest API documentation",
                version = "v1",
                contact = @Contact(
                        name = "Nirmit Jain",
                        email = "awsnirmit@gmail.com",
                        url= "https://www.test.com"

                ),
                license = @License(
                        name = "Apache2.0",
                        url = "https://www.test.com"

                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Sample bank Accounts microservices REST API documentation",
                 url = "https://www.test.com"
        )
)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
