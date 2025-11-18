package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"subject_id", "name"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Topic extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Subject subject;

    @Column(nullable = false)
    private String name;

    private String image;
}