package com.lingosphinx.quiz.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topic")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TopicDto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectDto subject;

    @Column(nullable = false)
    private String name;
}