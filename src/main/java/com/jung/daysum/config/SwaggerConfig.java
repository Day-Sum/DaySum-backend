package com.jung.daysum.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.env}")
    private String serverEnv;

    @Value("${server.url}")
    private String serverUrl;


    @Bean
    public OpenAPI openAPI() {

        String authName = "JWT";

        SecurityRequirement securityRequirement =
                new SecurityRequirement().addList(authName);

        Components components = new Components()
                .addSecuritySchemes(
                        authName,
                        new SecurityScheme()
                                .name(authName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("'Bearer '를 제외한 Access Token만 입력하세요.")
                );

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .info(apiInfo())
                .servers(apiServer());
    }


    private Info apiInfo() {

        return new Info()
                .title("DaySum - Swagger API")
                .description("DaySum REST API 명세서")
                .version("1.0.0");
    }


    private List<Server> apiServer() {

        String description = "Local Server";

        if(serverEnv.equals("prod")) {
            description = "Prod Server";
        }

        Server server = new Server()
                .description(description)
                .url(serverUrl);

        return List.of(server);
    }
}