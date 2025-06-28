package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrialDto {

    private String id;
    private String userId;
    private QuestionDto question;
    private Instant lastAttemptedAt;
    private int intervalDays;
    private double easeFactor;
    private int successCount;
    private int failureCount;
    private Instant nextDueDate;
    @Builder.Default
    private List<AttemptDto> attempts = new ArrayList<>();
}
