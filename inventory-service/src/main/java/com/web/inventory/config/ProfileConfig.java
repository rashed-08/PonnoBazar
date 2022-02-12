package com.web.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Profile("test")
    @Bean
    public void testDatabaseConfiguration() {

    }

    @Profile("dev")
    @Bean
    public void devDatabaseConfiguration() {

    }
    @Profile("prod")
    @Bean
    public void prodDatabaseConfiguration() {

    }

}
