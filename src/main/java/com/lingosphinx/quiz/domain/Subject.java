package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LanguageCode language;
}