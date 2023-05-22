package com.example.tgbotspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
public class TgbotSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TgbotSpringBootApplication.class, args);
    }
}
