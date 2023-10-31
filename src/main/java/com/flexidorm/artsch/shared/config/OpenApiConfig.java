package com.flexidorm.artsch.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenConfig() {
        return new OpenAPI()
            .info(new Info()
                .title("FlexDorm API")
                .description("Documentación del backend de FlexDorm")
                .version("1.0.0")
            );
    }
}
