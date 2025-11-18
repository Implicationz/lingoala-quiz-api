package com.lingosphinx.quiz.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TopicDto {
    private Long id;
    private SubjectDto subject;
    private String name;
    private String image;
}