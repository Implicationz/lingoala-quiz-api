package com.lingosphinx.quiz.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Explanation {
    @Column(length = 2048)
    private String text;

    @Column(length = 2048)
    private String translation;

    @Column(length = 2048)
    private String transcription;
}
