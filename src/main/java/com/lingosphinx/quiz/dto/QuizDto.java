package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.QuizTag;
import com.lingosphinx.quiz.domain.Visibility;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class QuizDto {
    private Long id;
    private LanguageCode language;
    private String name;
    private StudentDto owner;

    @Builder.Default
    private Visibility visibility = Visibility.PRIVATE;

    @Builder.Default
    private List<QuestionDto> questions = new ArrayList<>();
    @Builder.Default
    private Set<QuizTag> tags = new HashSet<>();
    private String source;
    private String image;
}