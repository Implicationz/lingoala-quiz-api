package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionSpecifications {

    public static Specification<Question> studyListEnabledForUser(UUID userId) {
        return (root, query, cb) -> {
            var subquery = query.subquery(Long.class);
            var sliRoot = subquery.from(StudyListItem.class);
            subquery.select(sliRoot.get("id"))
                    .where(
                            cb.equal(sliRoot.get("quiz").get("id"), root.get("quiz").get("id")),
                            cb.equal(sliRoot.get("studyList").get("userId"), userId),
                            cb.equal(sliRoot.get("newQuestions"), StudyStatus.ENABLED)
                    );
            return cb.exists(subquery);
        };
    }

    public static Specification<Question> noTrialForUser(UUID userId) {
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

    public static Specification<Question> quizIdEquals(Long quizId) {
        return (root, query, cb) -> cb.equal(root.get("quiz").get("id"), quizId);
    }

    public static Specification<Question> quizLanguageEquals(LanguageCode language) {
        return (root, query, cb) -> cb.equal(root.get("quiz").get("language"), language.getValue());
    }
    public static Specification<Question> isNew(UUID userId, LanguageCode language) {
        var spec = studyListEnabledForUser(userId).and(noTrialForUser(userId)).and(quizLanguageEquals(language));
        return spec;
    }

    public static Specification<Question> isNew(UUID userId, Long quizId) {
        var spec = noTrialForUser(userId).and(quizIdEquals(quizId));
        return spec;
    }

    public static Specification<Question> randomOrder() {
        final var seed = ThreadLocalRandom.current().nextDouble();
        return (root, query, cb) -> {
            query.orderBy(
                    cb.asc(cb.abs(cb.diff(root.get("randomSeed"), seed)))
            );
            return cb.conjunction();
        };
    }
}