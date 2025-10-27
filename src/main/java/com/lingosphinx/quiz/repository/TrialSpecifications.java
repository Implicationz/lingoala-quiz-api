package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.StudyListItem;
import com.lingosphinx.quiz.domain.StudyStatus;
import com.lingosphinx.quiz.domain.Trial;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TrialSpecifications {

    public static Specification<Trial> studyListEnabledForUser(UUID userId) {
        return (root, query, cb) -> {
            var subquery = query.subquery(Long.class);
            var sliRoot = subquery.from(StudyListItem.class);
            subquery.select(sliRoot.get("id"))
                    .where(
                            cb.equal(sliRoot.get("quiz").get("id"), root.get("question").get("quiz").get("id")),
                            cb.equal(sliRoot.get("studyList").get("userId"), userId),
                            cb.equal(sliRoot.get("dueQuestions"), StudyStatus.ENABLED)
                    );
            return cb.exists(subquery);
        };
    }

    public static Specification<Trial> userIdEquals(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get("userId"), userId);
    }

    public static Specification<Trial> nextDueDateBeforeOrEqual(Instant date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("nextDueDate"), date);
    }

    public static Specification<Trial> quizIdEquals(Long quizId) {
        return (root, query, cb) -> cb.equal(root.get("question").get("quiz").get("id"), quizId);
    }

    public static Specification<Trial> quizLanguageEquals(LanguageCode language) {
        return (root, query, cb) -> cb.equal(root.get("question").get("quiz").get("language"), language.getValue());
    }

    public static Specification<Trial> isDue(UUID userId, LanguageCode language) {
        var today = Instant.now();
        var spec = userIdEquals(userId).and(studyListEnabledForUser(userId))
                .and(nextDueDateBeforeOrEqual(today))
                .and(quizLanguageEquals(language));
        return spec;
    }

    public static Specification<Trial> isDue(UUID userId, Long quizId) {
        var today = Instant.now();
        var spec = userIdEquals(userId)
                .and(nextDueDateBeforeOrEqual(today))
                .and(quizIdEquals(quizId));
        return spec;
    }
    public static Specification<Trial> randomOrder() {
        final var seed = ThreadLocalRandom.current().nextDouble();
        return (root, query, cb) -> {
            query.orderBy(
                    cb.asc(cb.abs(cb.diff(root.get("question").get("randomSeed"), seed)))
            );
            return cb.conjunction();
        };
    }
}