package com.hcmute.tlcn2021.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class UtilsConfig {

    @Bean
    public ModelMapper modelMapper() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(LocalDateTime.class, String.class).setConverter(context -> context.getSource().format(formatter));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
