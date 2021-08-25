package com.hcmute.tlcn2021.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
