package com.company.application.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

@Configuration
@EnableScheduling
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper(List<AbstractConverter> converters) {
        ModelMapper modelMapper = new ModelMapper();
        converters.forEach(modelMapper::addConverter);
        return modelMapper;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

}
