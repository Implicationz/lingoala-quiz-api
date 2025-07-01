package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.LanguageCode;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectDto {
    private Long id;
    private String name;
    private LanguageCode language;
}