package com.lingosphinx.quiz.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageCode {
    public static final LanguageCode ENGLISH = LanguageCode.builder().value("english").build();
    String value;
}
