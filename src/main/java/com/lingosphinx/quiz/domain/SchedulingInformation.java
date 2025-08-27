package com.lingosphinx.quiz.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchedulingInformation {

    @Builder.Default
    @Column(name = "interval_days")
    private int intervalDays = 1;

    @Builder.Default
    @Column(name = "ease_factor")
    private double easeFactor = 2.5;


    public void schedule(SchedulingStrategy strategy, boolean success) {
        strategy.schedule(this, success);
    }

}
