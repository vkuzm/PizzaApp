package com.vkuzmenko.pizza.admin.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestConfiguration {

    @Bean("json")
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        final MappingJackson2HttpMessageConverter converter
                = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = converter.getObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        converter.setObjectMapper(objectMapper);
        restTemplate.getMessageConverters().add(converter);

        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        final MappingJackson2HttpMessageConverter converter
                = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);

        return builder.messageConverters(converter).build();
    }
}
