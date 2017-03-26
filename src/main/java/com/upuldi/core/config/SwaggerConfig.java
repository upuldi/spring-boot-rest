package com.upuldi.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Configuration for SwaggerDocs
 *
 * Created by udoluweera on 3/26/17.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${application.title}")
    private String title;

    @Value("${application.description}")
    private String description;

    @Value("${application.api.public.version}")
    private String version;

    @Value("${application.tos}")
    private String tos;

    @Value("${application.name}")
    private String name;

    @Value("${application.url}")
    private String url;

    @Value("${application.contact.email}")
    private String email;

    @Value("${application.license}")
    private String license;

    @Value("${application.apiLicenseUrl}")
    private String apiLicenseUrl;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(regex("/public/api.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                title,
                description,
                tos,
                "",
                new Contact(name,url,email),
                license,
                apiLicenseUrl);
        return apiInfo;
    }

}
