package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "answer")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Answer extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
    @Column(columnDefinition = "TEXT")
    private String translation;
    @Column(columnDefinition = "TEXT")
    private String transcription;

    @Column(nullable = false)
    private boolean correct;
}