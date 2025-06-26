package com.lingosphinx.quiz.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class QuizDto {
    private String id;
    private String language;
    private TopicDto topic;
    private String name;
    private String userId;

    @Builder.Default
    private List<QuestionDto> questions = new ArrayList<>();
    private String source;
    private String image;
}