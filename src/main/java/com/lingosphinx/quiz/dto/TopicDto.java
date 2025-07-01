package com.lingosphinx.quiz.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TopicDto {
    private Long id;
    private SubjectDto subject;
    private String name;
}