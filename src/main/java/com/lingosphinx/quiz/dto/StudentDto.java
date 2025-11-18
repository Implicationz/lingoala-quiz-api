package com.lingosphinx.quiz.dto;

import com.lingosphinx.quiz.domain.AccessLevel;
import lombok.*;

import java.util.UUID;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto {

    private Long id;
    private UUID userId;

    @Builder.Default
    private AccessLevel accessLevel = AccessLevel.FREE;

    private String name;

}