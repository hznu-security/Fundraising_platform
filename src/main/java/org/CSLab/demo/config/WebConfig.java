package org.CSLab.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override   // 无逻辑页面跳转
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("main");
    }

}
