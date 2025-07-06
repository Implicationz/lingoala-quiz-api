package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.Trial;
import com.lingosphinx.quiz.domain.Quiz;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.UUID;

public class TrialSpecifications {
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

    public static Specification<Trial> withQuestionAndAnswersFetch() {
        return (root, query, cb) -> {
            if (Trial.class.equals(query.getResultType())) {
                root.fetch("question", JoinType.INNER);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Trial> randomOrder() {
        return (root, query, cb) -> {
            query.orderBy(cb.asc(cb.function("RANDOM", Double.class)));
            return cb.conjunction();
        };
    }
}