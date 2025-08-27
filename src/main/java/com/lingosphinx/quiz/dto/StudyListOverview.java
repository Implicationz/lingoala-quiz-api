package com.lingosphinx.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StudyListOverview {
    final private long newCount;
    final private long dueCount;
    final private List<StudyListOverviewItem> items;
}
