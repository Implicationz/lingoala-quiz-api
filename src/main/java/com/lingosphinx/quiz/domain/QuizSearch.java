package com.lingosphinx.quiz.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSearch {
    private LanguageCode language;
    private Subject subject;
    private Topic topic;
    private List<QuizSearchHit> hits;
    private QuizSearchSort sort;
}
