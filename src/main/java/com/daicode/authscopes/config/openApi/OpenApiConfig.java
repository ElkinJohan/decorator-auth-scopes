package com.daicode.authscopes.config.openApi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Clase de configuración para OpenAPI, que define los detalles de la API expuesta, como el servidor, el contacto,
 * la licencia, y la información general del proyecto, utilizando las propiedades definidas en {@link OpenApiProperties}.
 */

@Getter
@Setter
@RequiredArgsConstructor
@Configuration
public class OpenApiConfig {

    /**
     * La URL del servidor de desarrollo para la documentación de OpenAPI.
     */
    @Value("${springdoc.openapi.dev-url}")
    private String devUrl;

    /**
     * Propiedades relacionadas con la configuración de OpenAPI. Estas incluyen la descripción del proyecto,
     * el nombre del desarrollador, la URL de la organización y otros detalles.
     */
    private final OpenApiProperties openApiProperties;

    /**
     * Configura la instancia de OpenAPI con la información del proyecto, incluyendo el servidor, el contacto
     * y la licencia del proyecto. La información se toma de las propiedades definidas en {@link OpenApiProperties}.
     *
     * @return una instancia de {@link OpenAPI} que contiene toda la configuración necesaria para la documentación de la API.
     */
    @Bean
    public OpenAPI myOpenAPI() {
        // Configuración del servidor de desarrollo
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription(this.openApiProperties.getProjectShortDescription());

        // Configuración del contacto
        Contact contact = new Contact();
        contact.setName(this.openApiProperties.getDeveloperName());
        contact.setUrl(this.openApiProperties.getOrganizationUrl());

        // Configuración de la licencia
        License mitLicense = new License()
                .name("MIT License")
                .url(this.openApiProperties.getProjectLicenceLink());

        // Configuración de la información general del API
        Info info = new Info()
                .title(this.openApiProperties.getProjectName())
                .version("1.0")
                .contact(contact)
                .description(this.openApiProperties.getProjectShortDescription())
                .termsOfService(this.openApiProperties.getProjectLicenceMsg())
                .license(mitLicense);

        // Devolver la configuración completa de OpenAPI
        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }
}
