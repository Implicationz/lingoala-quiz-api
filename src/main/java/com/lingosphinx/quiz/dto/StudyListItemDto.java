package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.StudyStatus;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class StudyListItemDto {

    private Long id;
    private StudyListDto studyList;
    private QuizDto quiz;

    @Builder.Default
    private StudyStatus newQuestions = StudyStatus.DISABLED;

    @Builder.Default
    private StudyStatus dueQuestions = StudyStatus.DISABLED;
}


