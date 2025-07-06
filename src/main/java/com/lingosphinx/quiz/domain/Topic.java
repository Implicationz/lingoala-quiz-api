package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topic")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(nullable = false, unique = true)
    private String name;
}