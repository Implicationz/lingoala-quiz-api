package com.lingosphinx.quiz.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Explanation {
    private String text;
    private String translation;
    private String transcription;
}
