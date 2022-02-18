package com.dhf.dh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class Mvcmapper implements WebMvcConfigurer {
    @Override
    //不需要为每一个页面写contor,
    public void addViewControllers(ViewControllerRegistry registry) {
        //请求的路径 转发到的页面
        registry.addViewController("login.html").setViewName("login");
        registry.addViewController("register.html").setViewName("register");
        registry.addViewController("index.html").setViewName("index");
        registry.addViewController("selall.html").setViewName("selall");
        registry.addViewController("addorder.html").setViewName("addorder");
        registry.addViewController("order.html").setViewName("order");
        registry.addViewController("upbalance.html").setViewName("upbalance");
        registry.addViewController("selbalanlog.html").setViewName("selbalanlog");
        registry.addViewController("seldayorder.html").setViewName("seldayorder");
        registry.addViewController("weixin.html").setViewName("weixin");

    }
}
