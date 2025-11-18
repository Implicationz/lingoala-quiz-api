package com.lingosphinx.quiz.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfiguration {

    private final GamificationClientProperties gamificationClientProperties;

    @Bean
    public RequestInterceptor apiKeyRequestInterceptor() {
        return template -> template.header("X-API-KEY", gamificationClientProperties.getApiKey());
    }

    @Bean
    public RequestInterceptor jwtRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                var authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.getCredentials() instanceof Jwt) {
                    var jwt = (Jwt) authentication.getCredentials();
                    template.header("Authorization", "Bearer " + jwt.getTokenValue());
                }
            }
        };
    }
}