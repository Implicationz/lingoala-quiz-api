package com.lingosphinx.quiz.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectDto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String language;
}