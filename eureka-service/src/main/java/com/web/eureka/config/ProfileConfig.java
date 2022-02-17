package com.web.eureka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Profile("test")
    @Bean
    public void testConfiguration() {

    }

    @Profile("dev")
    @Bean
    public void devConfiguration() {

    }

}
