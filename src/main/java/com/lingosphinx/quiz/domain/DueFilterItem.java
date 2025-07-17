package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class DueFilterItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private DueFilter filter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Quiz quiz;
}


