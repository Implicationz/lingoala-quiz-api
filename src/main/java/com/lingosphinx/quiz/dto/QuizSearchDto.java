package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.QuizSearchSort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizSearchDto {
    private LanguageCode language;
    private SubjectDto subject;
    private TopicDto topic;
    private List<QuizSearchHitDto> hits;
    private QuizSearchSort sort;
}
