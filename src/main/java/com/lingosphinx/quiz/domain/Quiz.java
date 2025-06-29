package com.lingosphinx.quiz.domain;

import com.lingosphinx.quiz.domain.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "quiz", indexes = {
    @Index(columnList = "language")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @Column(nullable = false)
    private LanguageCode language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column
    private String name;

    @Column(name = "user_id")
    private String userId;

    @Builder.Default
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @Column
    private String source;

    @Column
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