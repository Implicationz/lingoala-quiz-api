package com.lingosphinx.quiz.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class QuestionDto {

    private Long id;
    private QuizDto quiz;
    private String text;
    private String translation;
    private String transcription;
    private String image;
    private ExplanationDto explanation;
    @Builder.Default
    private List<AnswerDto> answers = new ArrayList<>();
    private int difficulty;
}


