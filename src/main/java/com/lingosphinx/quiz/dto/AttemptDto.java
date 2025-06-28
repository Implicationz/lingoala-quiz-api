package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.Answer;
import com.lingosphinx.quiz.domain.Trial;
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
