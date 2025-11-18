package com.lingosphinx.gamification.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class GoalDto {
    private Long id;
    private GoalDefinitionDto definition;
    private Instant lastProgress;
    private long progress;
    private List<ObjectiveDto> objectives;
}