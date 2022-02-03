package com.sn.org.spboot3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfid implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

       // registry.addViewController("/login").setViewName("login");
        registry.addViewController("/hello").setViewName("hello");
    }


}
