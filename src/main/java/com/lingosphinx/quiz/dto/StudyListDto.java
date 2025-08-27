package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.LanguageCode;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class StudyListDto {

    private Long id;
    private UUID userId;
    private LanguageCode language;
    @Builder.Default
    private List<StudyListItemDto> items = new ArrayList<>();

}


