package br.gov.ce.detran.vistoriacfcapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
@ComponentScan(basePackages = "br.gov.ce.detran.vistoriacfcapi.web.controller")
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security", securityScheme()))
                .info(
                    new Info()
                            .title("Rest API - Spring Vistoria CFC")
                            .description("Api para gestão de vistorias para CRT")
                            .version("V1")
                            .contact(new Contact().name("Luthianni alves").email("luthgoran@gmail.com"))

                );
    }

    /**
     * @return
     */
    private SecurityScheme securityScheme() {
        return new SecurityScheme()
            .description("Insira um bearer token valido para prosseguir")
            .type(SecurityScheme.Type.HTTP)
            .in(SecurityScheme.In.HEADER)
            .scheme("bearer")
            .bearerFormat("JWT")
            .name("security");
    
    }
}
