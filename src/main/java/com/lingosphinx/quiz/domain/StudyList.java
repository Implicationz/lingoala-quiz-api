package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "language"})
)
public class StudyList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private LanguageCode language;

    @Builder.Default
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "studyList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyListItem> items = new ArrayList<>();
}


