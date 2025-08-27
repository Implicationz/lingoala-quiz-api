package com.lingosphinx.quiz.dto;

import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrialDto {

    private Long id;
    private UUID userId;
    private QuestionDto question;
    @Builder.Default
    private int successCount = 0;
    @Builder.Default
    private int failureCount = 0;
    @Builder.Default
    private Instant lastAttemptedAt = Instant.EPOCH;
    @Builder.Default
    private Instant nextDueDate = Instant.EPOCH;
    @Builder.Default
    private List<AttemptDto> attempts = new ArrayList<>();
}
