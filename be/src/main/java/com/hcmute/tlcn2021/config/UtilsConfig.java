package com.hcmute.tlcn2021.config;

import org.apache.commons.text.RandomStringGenerator;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class UtilsConfig {

    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ModelMapper modelMapper() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(LocalDateTime.class, String.class).setConverter(context -> context.getSource().format(formatter));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    @Bean
    public RandomStringGenerator passwordGenerator() {
        return new RandomStringGenerator.Builder().withinRange('0', 'z').build();
    }
}
