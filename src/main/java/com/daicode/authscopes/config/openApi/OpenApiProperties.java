package com.daicode.authscopes.config.openApi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración que contiene las propiedades utilizadas para configurar OpenAPI.
 * Estas propiedades se definen en el archivo de configuración de la aplicación y están
 * prefijadas con "springdoc.properties".
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "springdoc.properties")
public class OpenApiProperties {
    /**
     * Nombre del proyecto utilizado en la documentación de OpenAPI.
     */
    private String projectName;

    /**
     * Breve descripción del proyecto que se muestra en la documentación de OpenAPI.
     */
    private String projectShortDescription;

    /**
     * Nombre del desarrollador responsable del proyecto, utilizado en la sección de contacto de la documentación.
     */
    private String developerName;

    /**
     * Mensaje sobre los términos de servicio del proyecto.
     */
    private String projectTosMsg;

    /**
     * Enlace a los términos de servicio del proyecto.
     */
    private String projectTosLink;

    /**
     * Mensaje relacionado con la licencia del proyecto.
     */
    private String projectLicenceMsg;

    /**
     * Enlace a la licencia del proyecto.
     */
    private String projectLicenceLink;

    /**
     * URL de la organización que mantiene el proyecto.
     */
    private String organizationUrl;
}
