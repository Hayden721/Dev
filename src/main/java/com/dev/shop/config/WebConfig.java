package com.dev.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 외부파일 허용
        registry.addResourceHandler("/Users/apple/develop/upload-files/**")
                .addResourceLocations("file:///Users/apple/develop/upload-files/");
    }

}
