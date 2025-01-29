package kz.nur.energy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Energy API")
                        .version("v1")
                        .description("API documentation for the Energy application")
                        .contact(new Contact().name("Your Name").email("you@example.com"))
                        .license(new License().name("Apache 2.0")));
    }

}