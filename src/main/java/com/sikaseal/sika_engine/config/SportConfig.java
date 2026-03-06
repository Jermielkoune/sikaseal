package com.sikaseal.sika_engine.config;

import com.sikaseal.sika_engine.common.Coach;
import com.sikaseal.sika_engine.common.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {
    @Bean("aquatic")
    public Coach swimCoach() {
        return new SwimCoach();
    }
}
