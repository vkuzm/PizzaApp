package com.vkuzmenko.pizza.product.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200", "http://localhost:7000");
            }
        };
    }

    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
        Hibernate5Module h5module = new Hibernate5Module();
        h5module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        h5module.enable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);

        for (HttpMessageConverter<?> mc : converters) {
            if (mc instanceof MappingJackson2HttpMessageConverter
                    || mc instanceof MappingJackson2XmlHttpMessageConverter) {
                ((AbstractJackson2HttpMessageConverter) mc).getObjectMapper().registerModule(h5module);
            }
        }
    }
}
