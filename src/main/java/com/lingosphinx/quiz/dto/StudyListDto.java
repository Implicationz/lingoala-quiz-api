package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.LanguageCode;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class StudyListDto {

    private Long id;
    private StudentDto student;
    private LanguageCode language;
    @Builder.Default
    private List<StudyListItemDto> items = new ArrayList<>();

}


