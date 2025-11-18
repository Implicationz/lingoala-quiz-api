package com.lingosphinx.quiz.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectDto {
    private Long id;
    private String name;
    private String image;
    @Builder.Default
    private List<TopicDto> topics = new ArrayList<>();
}