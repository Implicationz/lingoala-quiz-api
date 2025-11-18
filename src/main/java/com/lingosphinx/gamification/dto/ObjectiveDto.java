package com.lingosphinx.gamification.dto;

import lombok.Data;

@Data
public class ObjectiveDto {
    private GoalDto parent;
    private GoalDto child;
}