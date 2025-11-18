package com.lingosphinx.quiz.dto;

import com.lingosphinx.gamification.dto.GoalDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSearchHitDto {
    private QuizDto quiz;
    private GoalDto goal;
}
