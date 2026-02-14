package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@BatchSize(size = 30)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Question extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
    @Column(columnDefinition = "TEXT")
    private String translation;
    @Column(columnDefinition = "TEXT")
    private String transcription;

    private String image;

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

    private Double randomSeed;

    @PrePersist
    protected void onContentPersist() {
        if (randomSeed == null)
            randomSeed = ThreadLocalRandom.current().nextDouble();
    }

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


