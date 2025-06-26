package com.lingosphinx.quiz.domain;

import com.lingosphinx.quiz.domain.Explanation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false, length = 2048)
    private String text;

    @Column(length = 2048)
    private String translation;

    @Column(length = 2048)
    private String transcription;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "text", column = @Column(name = "explanation_text")),
        @AttributeOverride(name = "translation", column = @Column(name = "explanation_translation")),
        @AttributeOverride(name = "transcription", column = @Column(name = "explanation_transcription"))
    })
    private Explanation explanation;

    @Builder.Default
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


