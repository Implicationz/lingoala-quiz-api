package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class StudyListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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


