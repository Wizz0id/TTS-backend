package com.example.TTS_web.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${audio.storage-path}")
    private String audioPath;
    @Value("${model.storage-path}")
    private String modelPath;
    @Value("${loss.storage-path}")
    private String lossPath;
    @Value("${accur.storage-path}")
    private String accuracyPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/audio/**")
                .addResourceLocations("file:" + audioPath + "/");

        registry.addResourceHandler("/models/**")
                .addResourceLocations("file:" + modelPath + "/");

        registry.addResourceHandler("/loss/**")
                .addResourceLocations("file:" + lossPath + "/");

        registry.addResourceHandler("/accurate/**")
                .addResourceLocations("file:" + accuracyPath + "/");
    }
}