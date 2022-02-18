package com.dhf.dh.config;

import com.dhf.dh.handler.Allhandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class HanglerConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Allhandler()).addPathPatterns("/**").
                excludePathPatterns("/login").excludePathPatterns("/login.html").
                excludePathPatterns("/insuser").excludePathPatterns("/js/**").
                excludePathPatterns("/register.html").excludePathPatterns("/favicon.ico");

    }

}
