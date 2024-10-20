package com.reference.accounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
-------------------------------------****************************--------------------------------------------
NOTE : use this if your packages like controller, repos, entities are in somewhere out of the main package which is
com.reference.accounts
so use it to tell spring boot to scan in somewhere else
-------------------------------------****************************--------------------------------------------
@ComponentScan("com.reference.accounts.controller")
@EnableJpaRepositories("com.reference.accounts.repository")
@EntityScan("com.reference.accounts.entity")
*/
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts microservice REST API Documentation",
                description = "Accounts microservice REST API Documentation created by Amr Yassen",
                version = "v1",
                contact = @Contact(
                        name = "Amr Yassen",
                        email = "amryassenfcis@gmail.com",
                        url = "www.google.com"
                )
        )
)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
