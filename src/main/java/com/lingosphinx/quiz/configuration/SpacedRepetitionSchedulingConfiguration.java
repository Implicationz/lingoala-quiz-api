package com.lingosphinx.quiz.configuration;

import com.lingosphinx.quiz.domain.SchedulingStrategy;
import com.lingosphinx.quiz.domain.SimplifiedSuperMemo2SchedulingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpacedRepetitionSchedulingConfiguration {

    @Bean
    public SchedulingStrategy schedulingStrategy() {
        return new SimplifiedSuperMemo2SchedulingStrategy();
    }

}
