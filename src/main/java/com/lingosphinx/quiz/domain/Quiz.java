package com.lingosphinx.quiz.domain;

import com.lingosphinx.quiz.domain.Topic;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.*;

@BatchSize(size = 20)
@Entity
@Table(name = "quiz", indexes = {
    @Index(columnList = "language")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private LanguageCode language;

    private String name;

    @Column(name = "user_id")
    private String userId;

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