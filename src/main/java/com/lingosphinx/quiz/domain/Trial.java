package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "last_attempted_at")
    private Instant lastAttemptedAt;

    @Column(name = "interval_days")
    private int intervalDays;

    @Column(name = "ease_factor")
    private double easeFactor;

    @Column(name = "success_count")
    private int successCount;

    @Column(name = "failure_count")
    private int failureCount;

    @Column(name = "next_due_date")
    private Instant nextDueDate;

    @Builder.Default
    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attempt> attempts = new ArrayList<>();

    public void recalculateDueDate() {
        var now = Instant.now();
        this.nextDueDate = now.plusSeconds((long) intervalDays * 24 * 3600);
        this.lastAttemptedAt = now;
    }

    public void failure() {
        this.failureCount++;
        this.easeFactor = Math.max(this.easeFactor - 0.2, 1.3);
        this.intervalDays = 1;
        recalculateDueDate();
    }

    public void success() {
        this.successCount++;
        this.easeFactor = Math.max(this.easeFactor + 0.1, 1.3);
        this.intervalDays = Math.max(1, (int) Math.round(this.intervalDays * this.easeFactor));
        recalculateDueDate();
    }

    public boolean isNew() {
        return successCount + failureCount < 1;
    }

    public void apply(Attempt attempt) {
        this.attempts.add(attempt);
        if(attempt.getAnswer().isCorrect()) {
            success();
        } else {
            failure();
        }
    }
}
