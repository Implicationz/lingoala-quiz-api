package com.lingosphinx.quiz.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class QuizTagDto {
    private Long id;
    private QuizDto quiz;
    private TagDto tag;
}


