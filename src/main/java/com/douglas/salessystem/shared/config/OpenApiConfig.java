package com.douglas.salessystem.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI salesSystemOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sales System API")
                        .description("API REST para gerenciamento de vendas")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Douglas Magalhães")
                                .email("seuemail@email.com"))
                        .license(new License()
                                .name("MIT")));
    }
}
