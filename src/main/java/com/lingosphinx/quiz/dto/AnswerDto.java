package com.lingosphinx.quiz.dto;

import lombok.*;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AnswerDto {

    private Long id;
    private QuestionDto question;
    private String text;
    private String translation;
    private String transcription;
    private boolean correct;
}