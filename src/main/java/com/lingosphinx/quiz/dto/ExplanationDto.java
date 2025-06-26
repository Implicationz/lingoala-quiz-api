package com.lingosphinx.quiz.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ExplanationDto {
    private String text;
    private String translation;
    private String transcription;
}
