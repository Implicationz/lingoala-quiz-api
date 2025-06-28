package com.lingosphinx.quiz.domain;

import java.time.Instant;

public class SimplifiedSuperMemo2SchedulingStrategy implements SchedulingStrategy {


    public void failure(SchedulingInformation information) {
        information.setEaseFactor(Math.max(information.getEaseFactor() + 0.1, 1.3));
        information.setIntervalDays(Math.max(1, (int) Math.round(information.getIntervalDays() * information.getEaseFactor())));
    }

    public void success(SchedulingInformation information) {
        information.setEaseFactor(Math.max(information.getEaseFactor() - 0.2, 1.3));
        information.setIntervalDays(1);
    }

    @Override
    public void schedule(SchedulingInformation information, boolean success) {
        if (success) {
            this.success(information);
        } else {
            this.failure(information);
        }
    }
}