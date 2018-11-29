package me.mocha.calendar.config;

import me.mocha.calendar.model.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("me.mocha.calendar.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .ignoredParameterTypes(User.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "ClassRoom Calendar API",
                "학급 공유 캘린더 API",
                "0.0.1-SNAPSHOT",
                "",
                new Contact("hwangseonu", "https://blog.mocha.ga", "hwangseonu12@naver.com"),
                "MIT",
                "https://github.com/hwangseonu/class-room-calendar/blob/master/LICENSE",
                Collections.emptyList()
        );
    }

}
