package org.motoc.gamelibrary.configuration;

import org.motoc.gamelibrary.technical.properties.OpenApiProperties;
import org.motoc.gamelibrary.technical.properties.PathProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties({PathProperties.class, OpenApiProperties.class})
public class AppConfig
        implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // todo useful ?
        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addViewController("/").setViewName("forward:/index.html");
    }

}