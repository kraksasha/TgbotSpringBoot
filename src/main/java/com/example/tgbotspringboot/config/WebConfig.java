package com.example.tgbotspringboot.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebConfig {
    final int size = 16 * 1024 * 1024;

    @Bean
    public WebClient createWebClient(){
        return WebClient.builder().baseUrl("https://api.hh.ru")
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                        .build()).build();
    }

    @Bean
    public ObjectMapper createObjectMapper(){
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
