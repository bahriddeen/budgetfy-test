package com.budgetfy.app.config.swagger;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the OpenAPI documentation of the BudgetFy application.
 * <p>
 * Defines the application information and a server for the OpenAPI documentation.
 * <p>
 * Also defines the Bearer Authentication security scheme with JWT format for use
 * in the application's authentication process.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "${spring.application.name}",
                version = "0.1",
                contact = @Contact(
                        name = "Application service support",
                        url = "https://t.me/ContactDeveloperBot")
        ), servers = {
        @Server(
//                url = "http://localhost:${server.port}",
//                description = "Local development"
                url = "https://4fde-188-113-208-215.ngrok-free.app",
                description = "Ngrok server"
        )
}
)
@SecurityScheme(
        name = OpenApi.BEARER,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApi {
    public static final String BEARER = "Bearer Authentication";
}