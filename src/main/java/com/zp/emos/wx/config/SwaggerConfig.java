package com.zp.emos.wx.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://blog.csdn.net/zwj1030711290/article/details/117235438
 * http://localhost:8080/emos-wx-api/swagger-ui/index.html
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket createRestApi(Environment environment) {
        Profiles profiles = Profiles.of("dev", "test","default");
        boolean isOpen = environment.acceptsProfiles(profiles);// 判断当前是否处于该环境

        AuthorizationScope scop = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes= {scop};
        SecurityReference reference = new SecurityReference("token", scopes);
        List<SecurityReference> refList = new ArrayList<>();
        refList.add(reference);
        SecurityContext context = SecurityContext.builder().securityReferences(refList).build();
        Docket docket = new Docket(DocumentationType.OAS_30).enable(isOpen);
        docket.securitySchemes(Arrays.asList(new ApiKey("token", "token", "header")))
                .securityContexts(Arrays.asList(context))
                .apiInfo(apiInfo()).select().paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
        return docket;
    }


    /*@Bean
    public Docket docketCategory() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                // 分组名称
                .groupName("Category")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yang.takeout.backend.controller"))
                // 过滤请求，只扫描请求以/category开头的接口
                .paths(PathSelectors.ant("/category/**"))
                .build();
    }

    @Bean
    public Docket docketEmployee() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                // 分组名称
                .groupName("Employee")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yang.takeout.backend.controller"))
                // 过滤请求，只扫描请求以/employee开头的接口
                .paths(PathSelectors.ant("/employee/**"))
                .build();
    }*/

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("EMOS在线办公系统")
                .description("文档基本描述")
                .contact(new Contact("zhengpanone", "https://www.github.com/zhengpanone", "zhengpanone@htomail.com"))
                .version("1.0")
                .extensions(new ArrayList<>())
                .build();
    }

}
