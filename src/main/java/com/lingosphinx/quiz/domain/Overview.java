package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Overview {
    final private long newCount;
    final private long dueCount;
}
