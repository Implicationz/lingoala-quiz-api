package com.lingosphinx.quiz.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoundDto {
    private StudyListDto studyList;
    private QuizDto quiz;
    private int newCount;
    private int dueCount;
    @Builder.Default
    private int limitCount = Integer.MAX_VALUE;
    @Builder.Default
    private List<TrialDto> trials = new ArrayList<>();
}