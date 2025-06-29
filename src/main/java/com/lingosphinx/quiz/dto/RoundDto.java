package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.domain.Trial;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoundDto {
    private LanguageCode language;
    private QuizDto quiz;
    private int newCount;
    private int dueCount;
    private int limitCount = Integer.MAX_VALUE;
    @Builder.Default
    private List<Trial> trials = new ArrayList<>();
}