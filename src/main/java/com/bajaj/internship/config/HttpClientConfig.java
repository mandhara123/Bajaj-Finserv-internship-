package com.bajaj.internship.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfig {
    
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(1 * 1024 * 1024)) // 1MB
                .build();
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}