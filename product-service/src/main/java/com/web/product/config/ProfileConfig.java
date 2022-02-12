package com.web.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Profile("test")
    @Bean
    public void testEnvironment() {

    }

    @Profile("dev")
    @Bean
    public void devEnvironment() {

    }

    @Profile("prod")
    @Bean
    public void prodEnvironment() {

    }

}
