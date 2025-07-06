package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "answer")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, length = 1024)
    private String text;

    @Column(length = 1024)
    private String translation;

    @Column(length = 1024)
    private String transcription;

    @Column(nullable = false)
    private boolean correct;
}