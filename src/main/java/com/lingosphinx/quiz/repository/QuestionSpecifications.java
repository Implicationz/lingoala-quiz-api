// src/main/java/com/lingosphinx/quiz/repository/QuestionSpecifications.java
package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Trial;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecifications {
    public static Specification<Question> noTrialForUser(String userId) {
        return (root, query, cb) -> {
            var subquery = query.subquery(Long.class);
            var trialRoot = subquery.from(Trial.class);
            subquery.select(cb.literal(1L))
                    .where(
                            cb.equal(trialRoot.get("question").get("id"), root.get("id")),
                            cb.equal(trialRoot.get("userId"), userId)
                    );
            return cb.not(cb.exists(subquery));
        };
    }

    public static Specification<Question> quizIdEquals(String quizId) {
        return (root, query, cb) -> cb.equal(root.get("quiz").get("id"), quizId);
    }

    public static Specification<Question> quizLanguageEquals(String language) {
        return (root, query, cb) -> cb.equal(root.get("quiz").get("language"), language);
    }

    public static Specification<Question> withAnswersFetch() {
        return (root, query, cb) -> {
            if (!Long.class.equals(query.getResultType()) && !long.class.equals(query.getResultType())) {
                root.fetch("answers", JoinType.LEFT);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Question> randomOrder() {
        return (root, query, cb) -> {
            query.orderBy(cb.asc(cb.function("RANDOM", Double.class)));
            return cb.conjunction();
        };
    }
}