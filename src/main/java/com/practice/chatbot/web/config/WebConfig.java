package com.practice.chatbot.web.config;

import com.practice.chatbot.security.argumentResolver.JwtAuthorizationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthorizationArgumentResolver jwtAuthorizationArgumentResolver;

    public WebConfig(JwtAuthorizationArgumentResolver jwtAuthorizationArgumentResolver) {
        this.jwtAuthorizationArgumentResolver = jwtAuthorizationArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jwtAuthorizationArgumentResolver);
    }
}