package com.lingosphinx.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trial", indexes = {
    @Index(columnList = "student_id"),
    @Index(columnList = "next_due_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Trial extends BaseEntity {

    @ManyToOne(optional = false)
    private Student student;

    @Builder.Default
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question = Question.builder().build();

    @Builder.Default
    @Column(name = "success_count")
    private int successCount = 0;

    @Builder.Default
    @Column(name = "failure_count")
    private int failureCount = 0;

    @Builder.Default
    @Column(name = "last_attempted_at")
    private Instant lastAttemptedAt = Instant.EPOCH;

    @Builder.Default
    @Column(name = "next_due_date")
    private Instant nextDueDate = Instant.EPOCH;

    @Transient
    private SchedulingStrategy schedulingStrategy;

    @Builder.Default
    @Embedded
    private SchedulingInformation schedulingInformation = new SchedulingInformation();

    @BatchSize(size = 20)
    @Builder.Default
    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attempt> attempts = new ArrayList<>();

    protected void recalculateDueDate() {
        var now = Instant.now();
        this.setNextDueDate(now.plusSeconds((long) this.schedulingInformation.getIntervalDays() * 24 * 3600));
    }

    public void failure() {
        this.failureCount++;
        this.setLastAttemptedAt(Instant.now());
        this.schedulingInformation.schedule(this.schedulingStrategy, false);
        this.recalculateDueDate();
    }

    public void success() {
        this.successCount++;
        this.setLastAttemptedAt(Instant.now());
        this.schedulingInformation.schedule(this.schedulingStrategy, true);
        this.recalculateDueDate();
    }

    public void apply(Attempt attempt) {
        this.schedulingStrategy.schedule(this.schedulingInformation, attempt.getAnswer().isCorrect());
        if(attempt.getAnswer().isCorrect()) {
            success();
        } else {
            failure();
        }
    }
}
