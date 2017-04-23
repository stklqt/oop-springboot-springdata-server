package de.andrena.springworkshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Autowired
    @Qualifier("halJacksonHttpMessageConverter")
    private TypeConstrainedMappingJackson2HttpMessageConverter halJacksonHttpMessageConverter;

    @Bean
    public RestTemplate halCapableRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        ArrayList<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(halJacksonHttpMessageConverter);
        converters.addAll(messageConverters);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

}
