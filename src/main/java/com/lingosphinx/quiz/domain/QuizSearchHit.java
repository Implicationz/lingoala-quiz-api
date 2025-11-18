package com.lingosphinx.quiz.domain;

import com.lingosphinx.gamification.dto.GoalDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSearchHit {
    private Quiz quiz;
    private GoalDto goal;
}
