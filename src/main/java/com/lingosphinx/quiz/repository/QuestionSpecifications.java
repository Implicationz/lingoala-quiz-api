package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.concurrent.ThreadLocalRandom;

public class QuestionSpecifications {

    public static Specification<Question> studyListEnabled(StudyList studyList) {
        return (root, query, cb) -> {
            var subquery = query.subquery(Long.class);
            var sliRoot = subquery.from(StudyListItem.class);
            subquery.select(sliRoot.get("id"))
                    .where(
                            cb.equal(sliRoot.get("quiz").get("id"), root.get("quiz").get("id")),
                            cb.equal(sliRoot.get("studyList"), studyList),
                            cb.equal(sliRoot.get("newQuestions"), StudyStatus.ENABLED)
                    );
            return cb.exists(subquery);
        };
    }

    public static Specification<Question> noTrialForStudent(Student student) {
        return (root, query, cb) -> {
            var subquery = query.subquery(Long.class);
            var trialRoot = subquery.from(Trial.class);
            subquery.select(cb.literal(1L))
                    .where(
                            cb.equal(trialRoot.get("question").get("id"), root.get("id")),
                            cb.equal(trialRoot.get("student"), student)
                    );
            return cb.not(cb.exists(subquery));
        };
    }

    public static Specification<Question> quizIdEquals(Long quizId) {
        return (root, query, cb) -> cb.equal(root.get("quiz").get("id"), quizId);
    }

    public static Specification<Question> isNew(Student student, StudyList studyList) {
        var spec = studyListEnabled(studyList).and(noTrialForStudent(student));
        return spec;
    }

    public static Specification<Question> isNew(Student student, Long quizId) {
        var spec = noTrialForStudent(student).and(quizIdEquals(quizId));
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