package com.lingosphinx.quiz.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "lingosphinx.gamification.client")
public class GamificationClientProperties {
    private String apiKey;
    private String url;
}