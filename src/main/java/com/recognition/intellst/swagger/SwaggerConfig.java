package com.recognition.intellst.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${intellst.swagger.host}")
    private String host;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.recognition.intellst.controller"))
                .build()
                .apiInfo(apiEndPointsInfo())
                .useDefaultResponseMessages(false)
                .genericModelSubstitutes(Optional.class);
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("IntellST API ")
                .description("IntellST a OpenCV based project")
                .version("1.0.0")
                .build();
    }
}
