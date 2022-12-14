package org.top.book_library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    //  дефолтный обработчик для авторизации
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("layout/login");
    }

}