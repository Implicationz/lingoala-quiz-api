package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@BatchSize(size = 30)
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
    @Column(columnDefinition = "TEXT")
    private String translation;
    @Column(columnDefinition = "TEXT")
    private String transcription;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "text", column = @Column(name = "explanation_text", columnDefinition = "TEXT")),
        @AttributeOverride(name = "translation", column = @Column(name = "explanation_translation", columnDefinition = "TEXT")),
        @AttributeOverride(name = "transcription", column = @Column(name = "explanation_transcription", columnDefinition = "TEXT"))
    })
    private Explanation explanation;

    @Builder.Default
    @BatchSize(size = 80)
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @Column(nullable = false)
    private int difficulty;

    public void linkAnswers() {
        var answers = this.getAnswers();
        if (answers == null) {
            return;
        }
        for (var answer : answers) {
            answer.setQuestion(this);
        }
    }
}


