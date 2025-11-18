package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@BatchSize(size = 20)
@Entity
@Table(name = "quiz", indexes = {
    @Index(columnList = "language")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Quiz extends BaseEntity {

    @ManyToOne
    private Subject subject;

    @ManyToOne(optional = true)
    private Topic topic;

    @Column(nullable = false)
    private LanguageCode language;

    private String name;

    @ManyToOne(optional = true)
    private Student owner;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.PRIVATE;

    @Builder.Default
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QuizTag> tags = new HashSet<>();

    private String source;
    private String image;

    public void linkQuestions() {
        var questions = this.getQuestions();
        if (questions == null) {
            return;
        }
        for (var question : questions) {
            question.setQuiz(this);
            question.linkAnswers();
        }
    }
}