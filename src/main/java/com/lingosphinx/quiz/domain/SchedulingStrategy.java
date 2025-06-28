package com.lingosphinx.quiz.domain;

public interface SchedulingStrategy {
    void schedule(SchedulingInformation information, boolean success);
}
