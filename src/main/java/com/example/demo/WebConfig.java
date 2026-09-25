package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File uploadFolder = new File(System.getProperty("user.dir"), "uploads");
        String absolutePath = uploadFolder.getAbsolutePath();

        if (!absolutePath.endsWith("/")) {
            absolutePath += "/";
        }

        // Mapeia http://localhost:8080/uploads/** para a pasta física /app/uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absolutePath);
    }
}