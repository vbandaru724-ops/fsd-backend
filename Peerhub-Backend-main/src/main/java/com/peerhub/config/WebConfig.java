package com.peerhub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins:}")
    private String allowedOrigins;

    private static final String[] DEFAULT_ORIGIN_PATTERNS = {
            "http://localhost:5173",
            "http://localhost:5174",
            "https://suchir7.github.io",
            "https://*.vercel.app"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        Set<String> patterns = new LinkedHashSet<>(Arrays.asList(DEFAULT_ORIGIN_PATTERNS));
        if (allowedOrigins != null && !allowedOrigins.isBlank()) {
            Arrays.stream(allowedOrigins.split(","))
                    .map(String::trim)
                    .filter(origin -> !origin.isBlank())
                    .forEach(patterns::add);
        }

        registry.addMapping("/**")
                .allowedOriginPatterns(patterns.toArray(String[]::new))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
