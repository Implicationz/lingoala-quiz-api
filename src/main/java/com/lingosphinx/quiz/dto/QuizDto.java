package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.LanguageCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class QuizDto {
    private Long id;
    private LanguageCode language;
    private TopicDto topic;
    private String name;
    private String userId;

    @Builder.Default
    private List<QuestionDto> questions = new ArrayList<>();
    private String source;
    private String image;
}