package com.zp.emos.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * XssFilter->OAuth2Filter->Controller->TokenAspect
 * XssFilter是标准的Servlet过滤器，他的执行的优先级高于ShiroFilter和AOP拦截器。
 * XssFilter使用@WebFilter注解定义出来的过滤器，所以它的优先级比SpringMVC中注册的Filter优先级更高，所以XssFilter早于SpringMVC执行
 */
@SpringBootApplication
@ServletComponentScan
public class EmosWxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmosWxApiApplication.class, args);
    }

}
