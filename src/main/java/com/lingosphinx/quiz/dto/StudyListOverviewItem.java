package com.lingosphinx.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StudyListOverviewItem {
    final private long quiz;
    final private long newCount;
    final private long dueCount;
}
