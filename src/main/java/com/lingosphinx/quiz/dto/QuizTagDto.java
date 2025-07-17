package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.domain.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class QuizTagDto {
    private Long id;
    private QuizDto quiz;
    private TagDto tag;
}


