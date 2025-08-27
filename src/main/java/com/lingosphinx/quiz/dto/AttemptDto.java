package com.lingosphinx.quiz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttemptDto {
    private TrialDto trial;
    private AnswerDto answer;
}
