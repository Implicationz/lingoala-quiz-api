package com.lingosphinx.quiz.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TagDto {
    private Long id;
    private String name;
}


