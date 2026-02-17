package com.lingosphinx.quiz.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Round {
    private StudyList studyList;
    private Quiz quiz;
    private int newCount;
    private int dueCount;
    @Builder.Default
    private int limitCount = Integer.MAX_VALUE;
    @Builder.Default
    private List<Trial> trials = new ArrayList<>();

    public void shuffleAnswers() {
        trials.forEach(Trial::shuffleAnswers);
    }

    public void shuffleQuestions() {
        Collections.shuffle(this.trials);
    }

    public void shuffle() {
        this.shuffleAnswers();
        this.shuffleQuestions();
    }

    public void limitTrials(int count) {
        if (trials.size() > count) {
            trials = trials.subList(0, count);
        }
    }
}