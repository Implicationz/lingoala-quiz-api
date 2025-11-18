package com.lingosphinx.gamification.dto;

import lombok.Data;

@Data
public class ObjectiveDefinitionDto {
    private GoalDefinitionDto parent;
    private GoalDefinitionDto child;
}