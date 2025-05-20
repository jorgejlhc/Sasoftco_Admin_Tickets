package com.example.tickets.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gestión de Tickets API")
                        .version("1.0")
                        .description("Documentación de la API para el Sistema de Gestión de Tickets")
                        .contact(new Contact()
                                .name("Soporte")
                                .email("jorgejlhc@gmail.com")));
    }
}