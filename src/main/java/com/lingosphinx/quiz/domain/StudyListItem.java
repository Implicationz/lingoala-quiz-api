package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudyListItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private StudyList studyList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Quiz quiz;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyStatus newQuestions = StudyStatus.DISABLED;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyStatus dueQuestions = StudyStatus.DISABLED;
}


