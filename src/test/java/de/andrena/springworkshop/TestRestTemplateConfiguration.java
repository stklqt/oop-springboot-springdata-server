package de.andrena.springworkshop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("test")
public class TestRestTemplateConfiguration {

    @Bean
    public RestTemplate halCapableRestTemplate() {
        return new RestTemplate();
    }

}